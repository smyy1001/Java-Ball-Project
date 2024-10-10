javac -cp .:/PATH/TO/Java-Ball-Project/lib/ext/vecmath.jar:/PATH/TO/Java-Ball-Project/lib/ext/j3dcore.jar:/PATH/TO/Java-Ball-Project/lib/ext/j3dutils.jar src/*.java


java --add-exports java.desktop/sun.awt=ALL-UNNAMED -cp src:/PATH/TO/Java-Ball-Project/lib/ext/vecmath.jar:/PATH/TO/Java-Ball-Project/lib/ext/j3dcore.jar:/PATH/TO/Java-Ball-Project/lib/ext/j3dutils.jar -Djava.library.path=/PATH/TO/Java-Ball-Project/lib/amd64 App
