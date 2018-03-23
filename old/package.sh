echo "Compiling ...."

mvn package

mvn compile war:war

echo "Done compiling .... Copying War to JBOSS_HOME/standalone/deployments"

cd target

cp school.war "$JBOSS_HOME"/standalone/deployments/  


