# Git commands

## Pull

Before you start work on your local repo, pull the changes from the remote one. DO THIS EVERY TIME.
``git pull``

## Add

Before committing your changes, you have to add the files you changed. Here's `filename.txt` as an example:
``git add filename.txt``

You can also add all the files in your directory and all of the subdirectories with:
``git add .``

## Commit

After adding all of your files, you have to commit them.
``git commit -m "Your message here"``
Or, if you want a longer commit message, you can do 
``git commit``
and a Vim file will open where you can write your message:
![Vim commit](/assets/images/vimcommit.png)

## Push
After committing, you will have to push your changes to the remote repository.
``git push origin master``