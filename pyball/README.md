Python implementation of the Foosball league manager

# Setup Python
Download and install Python 2.7

* http://www.python.org/

# Setup Django
Download and install Django 1.4

* https://www.djangoproject.com/

# Setup App

## Setup DB

* Create the following directory structure on your machine:
c:/temp/foosball/data

* From within pyball_site/ run the follwing command
python manage.py syncdb

Note: The first time you run this command you will be prompted to create a super user.

### Run the server

* Run the following command from within pyball_site
python manage.py runserver

# Useful Examples

* https://docs.djangoproject.com/en/1.4/intro/tutorial01/