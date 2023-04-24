#!/usr/bin/env bash

show_help() {
cat << EOF
Usage: ${0##*/}
    Required env vars:
      REPO_URL             Repository url
      REPO_BRANCH          The name of the branch
      GIT_USERNAME        Git user
      GIT_PASSWORD        Git password
    Optional env vars:
      GIT_CLONE_DIR       Checkout directory
      GIT_SHALLOW_DEPTH   Shallow depth for the clone
EOF
}

if [ -z "$REPO_URL" ]
then
  echo "ERROR: REPO_URL is required"
  show_help
  exit 1
elif [ -z $(echo "$REPO_URL" | grep -Eo '^http[s]?://[^/]+') ]
then
  echo "ERROR: REPO_URL $REPO_URL is not valid"
  show_help
  exit 1
fi

if [ -z "$REPO_BRANCH" ]
then
  echo "ERROR: REPO_BRANCH is required"
  show_help
  exit 1
fi

if [ -z "$GIT_USERNAME" ]
then
  echo "ERROR: GIT_USERNAME is required"
  show_help
  exit 1
fi

if [ -z "$GIT_PASSWORD" ]
then
  echo "ERROR: GIT_PASSWORD is required"
  show_help
  exit 1
fi

#Set optional vars with default values
GIT_CLONE_DIR=${GIT_CLONE_DIR:-"."}
GIT_SHALLOW_DEPTH=${GIT_SHALLOW_DEPTH:-1}


#https://stackoverflow.com/questions/57732289/how-can-i-git-clone-over-https-with-a-personal-access-token-containing
GIT_PASSWORD_ENCODED=$(echo "$GIT_PASSWORD" | sed "s#/#%2F#g" )
GIT_CREDENTIAL="$GIT_USERNAME:$GIT_PASSWORD_ENCODED"

# Take the git url and place the required credentials

STRIP_URL=$(echo "$REPO_URL" | sed "s#https://##g")
CLONE_URL="https://$GIT_CREDENTIAL@$STRIP_URL"
# CLONE_URL=$(echo "$REPO_URL" | sed "s#\(https:\/\/\)\(.*\)#\1$GIT_CREDENTIAL@\2#" )

echo "Clone repo $REPO_URL to $GIT_CLONE_DIR"
git clone --branch $REPO_BRANCH --depth $GIT_SHALLOW_DEPTH $CLONE_URL $GIT_CLONE_DIR
