
Module { var <modulePath, <name, <arguments, <soundProcess;
         var <envir;

  *new { arg modulePath, name, arguments;
    modulePath = modulePath.standardizePath;
    ^super.newCopyArgs(modulePath, name, arguments).init.run;
  }

  init {
    envir = ();
  }

  run { var path;
    arguments = arguments.add(\module);
    arguments = arguments.add(this);
    path = PathName(modulePath +/+ name);
    if(File.exists(path.fullPath), {
      soundProcess = this.runFile(path, "run");
    },{("Module"+name+"not installed.").error});
  }

  stop { var path;
    path = modulePath +/+ name;
    path = PathName(path);
    this.runFile(path, "stop");
  }

  runFile { arg folder, file; var path;
    path = (folder.fullPath+/+file.asString++".scd");
    if (File.exists(path), {
      ^path.load.performKeyValuePairs(\value, arguments);
    });
  }

  free {
    this.stop;
  }

}
