To test this demo. 

Need

1. The last version of Juzu 0.6.0-SNAPSHOT.
 
   the this version don't exist yet on the repository Exo. We need build it manually. On the terminal, type the 
following command 

       git clone git://github.com/juzu/juzu.git
       cd juzu
       mvn clean install 

At this moment, Your local repository Maven contains all files jars relates juzu Framework with version 0.6.0-SNAPSHOT.

  
2. Gatein-3.4.0.Final or last version. 
    
    Make sure the compability of all version jars files what this demo need with gatein product. 
to do that, please see in  the file training-cross-tutorial-juzu/pom.xml and the directory Gatein-3.4.0.Final/libs



3. Make the portlet in dev-mode 
  
change the path of project to your own path.

in file ${worskpace}/training-cross-tutorial-juzu/src/main/webapp/WEB-INF/portlet.xml

replace /home/bangnv/workspace1/training-cross-tutorial-juzu/src/main/java to   Your_path/training-cross-tutorial-juzu/src/main/java


Building and Running
        
       -    Building training-cross-tutorial-juzu.war file by this command         mvn clean install 
       -    Copy the war file at target/training-cross-tutorial-juzu.war to Gatein-3.4.0.Final/
       -    Start the Gatein application and import applications from menu Group -> Administration -> Application Registry
       -    A New portlet with the name "Gallery Photos" will be added in the category juzu.

         

       


