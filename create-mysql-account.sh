#!/bin/bash
echo "Please enter your MySQL username here: "
read username
echo "Please enter your MySQL password here: "
read -s password
echo "CREATE USER '$username'@'localhost' IDENTIFIED BY '$pasword';" > temporary.sql
echo "GRANT ALL PRIVILEGES ON *.* TO '$username'@'localhost';" >> temporary.sql
echo "FLUSH PRIVILEGES;" >> temporary.sql
sudo mysql -p"$password" -u"root" < ./temporary.sql
rm temporary.sql
