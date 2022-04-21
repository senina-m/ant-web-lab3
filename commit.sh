#!/bin/bash
echo "Commit checker started"
CLASSES="${PWD}/commit_classes"

ANY_CHANGES=false

while CLASSES= read -r LINE
do
    SVN_STATUS=`svn status ${PWD}/${LINE}`
    #echo "${LINE}"
    echo $SVN_STATUS
    if  [[ SVN_STATUS -eq "" ]]; then
          echo "\$SVN_STATUS is empty => file wasn't modified"
    else
          echo "\$SVN_STATUS is NOT empty => file was modified and ready to commit"
          ANY_CHANGES=true
    fi
done < "${CLASSES}"

if [ $ANY_CHANGES ]; then
svn commit -m "auto-ant commit"
fi