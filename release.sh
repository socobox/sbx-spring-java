#!/bin/bash -e
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

export VERSION=$1

if [[ -z "$VERSION" ]]; then
  echo "ERROR: Version is undefined"
  exit 1
fi

mvn versions:set -DnewVersion=${VERSION} -DgenerateBackupPoms=false
mvn clean deploy -B -P release --settings ${DIR}/settings.xml
git checkout .
git tag $VERSION
git push upstream --tags
