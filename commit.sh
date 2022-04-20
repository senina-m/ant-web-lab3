#!/bin/bash
echo "Commit checker started"

CLASSES="${PWD##}/commit_classes"
echo $CLASSES  
ANY_CHANGES=false

while IFS= read -r LINE
do
    SVN_STATUS="svn status ${LINE}"
    echo $SVN_STATUS
    if [ -z "$SVN_STATUS" ]
    then
          echo "\$SVN_STATUS is empty => file wasn't modified"
    else
          echo "\$SVN_STATUS is NOT empty => file was modified and ready to commit"
          ANY_CHANGES=true
    fi
done < "${CLASSES}"

if [ $ANY_CHANGES ]; then
svn commit
fi