import datetime
from fabric.api import lcd, local

def create_release():
  new_version = get_release_version();
  print('New version number is: {}'.format(new_version))
  raw_input('Is new version number correct? [Enter to continue]')
  print('Creating release branch: {} ......'.format(new_version))
  local('git checkout develop')
  local('git pull')
  local('git checkout master')
  local('git pull')
  local('git flow release start {}'.format(new_version))
  local('git flow release publish {}'.format(new_version))

def start_release():
  new_version = get_release_version();
  raw_input('Green Light? [Enter to continue]')
  print('Switching to release branch: {} ......'.format(new_version))
  local('git checkout release/{}'.format(new_version))
  local('git pull')
  local('git flow release finish {} -m {} -k'.format(new_version, new_version))
  local('git push --tags')



def get_release_version():
  now = datetime.datetime.now();
  return now.strftime('%Y%m%d')