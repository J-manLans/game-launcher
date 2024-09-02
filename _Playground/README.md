# Playground
This is your personal playground, where everything except for those files explicitly stated will
be completely ignored by version control. The initial patterns excluded in **``.gitignore``**:

````
/*
/README.md
!.gitignore
!README.md
!pom.xml
!src/main/java/com/dt180g/playground/Playground.java
!src/test/java/com/dt180g/playground/PlaygroundTest.java
!src/main/java/package-info.java
````

As you can see there are some sources already defined, and this is to make sure the folder structure is retained since
Git doesn't allow versioning of empty directories. When you play around and don't want the code to be included in VCS, 
you should make sure to create new source documents.