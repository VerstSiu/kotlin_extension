#!/bin/bash
init_file="./npm-init.iml"
if [ ! -f "$init_file" ]; then
    # Install standard-version to local directory, speed up commands execution
    npm i --save-dev standard-version
    touch "$init_file"
    echo -e "\033[32m ======== standard-version install complete ========= \033[0m"
fi

echo "Which type would you like to publish:"
std_option=''
select version in "alpha" "beta" "rc" "release"
do
    case $version in
    "alpha")
    std_option='--prerelease alpha'
    ;;
    "beta")
    std_option='--prerelease beta'
    ;;
    "rc")
    std_option='--prerelease rc'
    ;;
    "release")
    std_option='--release'
    ;;
    *)
    echo "Invalid type!" ; exit
    ;;
    esac

    echo "Build type: $version"
    break
done

no_release=false
read -r -n1 -p "Execute with dry-run mode[y/N]? " pick_option
echo ""
if [[ $pick_option == "n" || $pick_option == "N" || $pick_option == "" ]]; then
    std_suffix=""
else
    std_suffix="--dry-run"
    no_release=true
fi

std_command="npx standard-version -t \"\" ${std_option} ${std_suffix}"
echo "Final execution command: $std_command Try running..."
$std_command

if [ $no_release == true ]; then
    echo "dry-run complete, exit script"
    exit 0
fi

read -r -n1 -p "Push to remote repository(execute git push)[Y/n]? " pick_option
echo ""
if [[ $pick_option == "y" || $pick_option == "Y" || $pick_option == "" ]]; then
    printf "\n Push running...\n"
    git push
else
    exit 0
fi

# Push tag
LAST_TAG=$(git for-each-ref --format '%(tag)' --sort=-taggerdate --count=1)
read -r -n1 -p "Push latest tag: ${LAST_TAG} [Y/n]? " pick_option
echo ""
if [[ $pick_option == "y" || $pick_option == "Y" || $pick_option == "" ]]; then
    git push origin "${LAST_TAG}"
fi