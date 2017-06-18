
echo "WELCOME TO SCHOOL MANAGEMENT SYSTEM. THE SYSTEM IS STATRTING .........."

echo ".......... Starting the server"

cd /opt/Programs/WildFly/8.2.0/bin 

  ./standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &



