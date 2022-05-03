import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;


public class DiffTask extends Task{
    private String username;
    private String password;
    //<path to repository root (not to a working copy>
    private String repositoryPath;
    //path to svn repo <path to the base svn repository>
    private String svnRepoBase;
    private String classesNamesPath;

    public void DiffTask(){}

    public void DiffTask(String username, String password, String repositoryPath, String svnRepoBase, String classesNamesPath){
        this.username = username;
        this.password = password;
        this.repositoryPath = repositoryPath;
        this.svnRepoBase = svnRepoBase;
        this.classesNamesPath = classesNamesPath;
    }

    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    public void setClassesNamesPath(String classesNamesPath){
        this.classesNamesPath = classesNamesPath;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSvnRepoBase(String svnRepoBase) { this.svnRepoBase = svnRepoBase;}

    @Override
    public void execute() throws BuildException {
        System.out.println("Diff start!");
        // BuildException
        try (BufferedReader br = new BufferedReader(new FileReader(classesNamesPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                SVNClientManager svnClientManager = getSVNClientManager();
               // process the line.
               if (checkChanged(svnClientManager, line)){
                   commitToSvn(svnClientManager, line);
                   break;
               }
            }
        } catch (IOException | SVNException e) {
            e.printStackTrace();
        }
    }

    //svn check if file was modified
    public boolean checkChanged(SVNClientManager svnClientManager, String classNamePath) throws SVNException {
        return getStatus(getSVNClientManager(), classNamePath) != null;
    }

//    https://stackoverflow.com/questions/23245684/ways-to-automate-svn-process-using-java
    public SVNClientManager getSVNClientManager() throws SVNException {
        FSRepositoryFactory.setup();
        SVNURL url = SVNURL.parseURIDecoded(svnRepoBase);
        SVNRepository repository = SVNRepositoryFactory.create(url, null);
        ISVNOptions myOptions = SVNWCUtil.createDefaultOptions(true);
        // ISVNAuthenticationManager myAuthManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        // repository.setAuthenticationManager(myAuthManager);
        //clientManager will be used to get different kind of svn clients instances to do different activities
        //like update, commit, view diff etc.
        return SVNClientManager.newInstance(myOptions);
    }

    private String getStatus(SVNClientManager clientManager, String classNamePath) throws SVNException {
        final SVNStatus status = clientManager.getStatusClient().doStatus(new File(classNamePath), false);
        return status != null ? status.getRevision().toString() : null;
    }

    public void commitToSvn(SVNClientManager clientManager, String classNamePath) throws SVNException {
        SVNCommitClient commitClient = clientManager.getCommitClient();
        File fileToCheckin = new File(classNamePath);
        boolean recursive = true;       
        SVNCommitInfo importInfo = commitClient.doImport(
                fileToCheckin,
                SVNURL.fromFile(new File(repositoryPath)),
            "auto commit",
                recursive);
        System.out.println(importInfo.getNewRevision());
    }
}
