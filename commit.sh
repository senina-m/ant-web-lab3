CLASSES="${PWD}/commit_classes"
echo $CLASSES  
ANY_CHANGES=false

while CLASSES= read -r LINE
do
    SVN_STATUS=`svn status ${PWD}/${LINE}`
    echo $SVN_STATUS
    if  [[ -z $SVN_STATUS ]]; then
          echo "${LINE} wasn't modified"
    else
          echo "${LINE} was modified and ready to commit"
          ANY_CHANGES=true
    fi
done < "${CLASSES}"

if [ $ANY_CHANGES ]; then
svn commit -m "auto-ant commit"
fi