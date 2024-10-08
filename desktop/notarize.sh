#!/bin/sh

#
#
# Pixel Dungeon
# Copyright (C) 2012-2015 Oleg Dolya
#
# Shattered Pixel Dungeon
# Copyright (C) 2014-2024 Evan Debenham
#
# Souls Pixel Dungeon
# Copyright (C) 2024 Bartolomeo Cold
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>
#

# This shell script helps automate the process of notarizing
# It is based on the guide found here: https://www.joelotter.com/2020/08/14/macos-java-notarization.html
# requires xcode tools, script may take a minute or two to run as it uploads results to apple

# usage is: "notarize.sh <path-to-.app> <path-to-entitlements-.plist> <certificate-name> <apple-id> <app-password>"
# There is no input validation to check your arguments!
APP="$1"
PLIST=`PWD`"/$2" #need absolute path
CERT="$3"
USER="$4"
PASS="$5"

#extracts the team ID from the certification name
TEAM="${CERT#*(}"
TEAM="${TEAM%)}"

#first sign the naked dylib in /Contents/runtime/Contents/MacOS/libjli.dylib
codesign --force --options runtime --timestamp --sign "$CERT" \
      --entitlements "$PLIST" "${APP}/Contents/runtime/Contents/MacOS/libjli.dylib"

#then iterate over each jar and sign all .dylib files within it
# to do this we have to unzip each JAR, sign the files and re-zip =/
# several commands are piped to dev/null to cut down on console spam
pushd "${APP}"/Contents/app/ > /dev/null
rm -rf jar/
for JAR in *.jar; do

  mkdir jar
  mv "$JAR" jar/
  pushd jar/ > /dev/null
  unzip "${JAR}" > /dev/null
  rm "${JAR}"

  for LIB in `find . -name '*.dylib'`; do
    codesign --force --options runtime --timestamp --sign "$CERT" \
      --entitlements "$PLIST" "${LIB}"
  done

  zip -r "../${JAR}" * > /dev/null
  popd > /dev/null
  rm -rf jar/

done
popd > /dev/null

#finally do one more deep sign on the whole .app
codesign --deep --force --options runtime --timestamp --sign "$CERT" \
      --entitlements "$PLIST" "${APP}"

#zip it up and send it to apple!
rm -rf "${APP}".zip
zip -r "${APP}".zip "${APP}" > /dev/null

echo "Uploading to apple, this may take a few minutes:"

xcrun notarytool submit "${APP}".zip \
  --apple-id "$USER" \
  --password "$PASS" \
  --team-id "$TEAM" \
  --wait

rm -rf "${APP}".zip

echo "Notarizing finished, stapling..."

xcrun stapler staple "${APP}"