
::set CLASSPATH = D:\Prem\GIT-PROJ\MyTestPrograms\SimpleRMIExample\dist\de.vogella.build.test.ant.jar;

::cd src
start rmiregistry
java -classpath "D:\Prem\GIT-PROJ\MyTestPrograms\SimpleRMIExample\dist\de.vogella.build.test.ant.jar" com.mkyong.rmiserver.ServerOperation