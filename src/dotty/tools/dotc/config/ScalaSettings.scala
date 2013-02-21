package dotty.tools.dotc.config

import PathResolver.Defaults

class ScalaSettings extends Settings.SettingGroup {

  protected def defaultClasspath = sys.env.getOrElse("CLASSPATH", ".")

  /** Path related settings.
   */
  val bootclasspath = PathSetting("-bootclasspath", "Override location of bootstrap class files.", Defaults.scalaBootClassPath)
  val extdirs = PathSetting("-extdirs", "Override location of installed extensions.", Defaults.scalaExtDirs)
  val javabootclasspath = PathSetting("-javabootclasspath", "Override java boot classpath.", Defaults.javaBootClassPath)
  val javaextdirs = PathSetting("-javaextdirs", "Override java extdirs classpath.", Defaults.javaExtDirs)
  val sourcepath = PathSetting("-sourcepath", "Specify location(s) of source files.", "") // Defaults.scalaSourcePath

  /** Other settings.
   */
  val dependencyfile = StringSetting("-dependencyfile", "file", "Set dependency tracking file.", ".scala_dependencies")
  val deprecation = BooleanSetting("-deprecation", "Emit warning and location for usages of deprecated APIs.")
  val encoding = StringSetting("-encoding", "encoding", "Specify character encoding used by source files.", Properties.sourceEncoding)
  val explaintypes = BooleanSetting("-explaintypes", "Explain type errors in more detail.")
  val feature = BooleanSetting("-feature", "Emit warning and location for usages of features that should be imported explicitly.")
  val g = ChoiceSetting("-g", "level", "Set level of generated debugging info.", List("none", "source", "line", "vars", "notailcalls"), "vars")
  val help = BooleanSetting("-help", "Print a synopsis of standard options")
  val nowarn = BooleanSetting("-nowarn", "Generate no warnings.")
  val print = BooleanSetting("-print", "Print program with Scala-specific features removed.")
  val target = ChoiceSetting("-target", "target", "Target platform for object files. All JVM 1.5 targets are deprecated.",
    List("jvm-1.5", "jvm-1.5-fjbg", "jvm-1.5-asm", "jvm-1.6", "jvm-1.7", "msil"),
    "jvm-1.6")
  val unchecked = BooleanSetting("-unchecked", "Enable additional warnings where generated code depends on assumptions.")
  val uniqid = BooleanSetting("-uniqid", "Uniquely tag all identifiers in debugging output.")
  val usejavacp = BooleanSetting("-usejavacp", "Utilize the java.class.path in classpath resolution.")
  val verbose = BooleanSetting("-verbose", "Output messages about what the compiler is doing.")
  val version = BooleanSetting("-version", "Print product version and exit.")

  val jvmargs = PrefixSetting("-J<flag>", "-J", "Pass <flag> directly to the runtime system.")
  val defines = PrefixSetting("-Dproperty=value", "-D", "Pass -Dproperty=value directly to the runtime system.")
  val toolcp = PathSetting("-toolcp", "Add to the runner classpath.", "")
  val nobootcp = BooleanSetting("-nobootcp", "Do not use the boot classpath for the scala jars.")

  val argfiles = BooleanSetting("@<file>", "A text file containing compiler arguments (options and source files)")
  val classpath = PathSetting("-classpath", "Specify where to find user class files.", defaultClasspath) withAbbreviation "-cp"
  val d = StringSetting("-d", "directory|jar", "destination for generated classfiles.", ".")
  val nospecialization = BooleanSetting("-no-specialization", "Ignore @specialize annotations.")
  val language = MultiStringSetting("-language", "feature", "Enable one or more language features.")

  /** -X "Advanced" settings
   */
  val Xhelp = BooleanSetting("-X", "Print a synopsis of advanced options.")
  val assemname = StringSetting("-Xassem-name", "file", "(Requires -target:msil) Name of the output assembly.", "").dependsOn(target, "msil")
  val assemrefs = StringSetting("-Xassem-path", "path", "(Requires -target:msil) List of assemblies referenced by the program.", ".").dependsOn(target, "msil")
  val assemextdirs = StringSetting("-Xassem-extdirs", "dirs", "(Requires -target:msil) List of directories containing assemblies.  default:lib", Defaults.scalaLibDir.path).dependsOn(target, "msil")
  val sourcedir = StringSetting("-Xsourcedir", "directory", "(Requires -target:msil) Mirror source folder structure in output directory.", ".").dependsOn(target, "msil")
  val checkInit = BooleanSetting("-Xcheckinit", "Wrap field accessors to throw an exception on uninitialized access.")
  val noassertions = BooleanSetting("-Xdisable-assertions", "Generate no assertions or assumptions.")
//  val elidebelow = IntSetting("-Xelide-below", "Calls to @elidable methods are omitted if method priority is lower than argument",
//    elidable.MINIMUM, None, elidable.byName get _)
  val noForwarders = BooleanSetting("-Xno-forwarders", "Do not generate static forwarders in mirror classes.")
  val genPhaseGraph = StringSetting("-Xgenerate-phase-graph", "file", "Generate the phase graphs (outputs .dot files) to fileX.dot.", "")
  val XlogImplicits = BooleanSetting("-Xlog-implicits", "Show more detail on why some implicits are not applicable.")
  val logImplicitConv = BooleanSetting("-Xlog-implicit-conversions", "Print a message whenever an implicit conversion is inserted.")
  val logReflectiveCalls = BooleanSetting("-Xlog-reflective-calls", "Print a message when a reflective method call is generated")
  val logFreeTerms = BooleanSetting("-Xlog-free-terms", "Print a message when reification creates a free term.")
  val logFreeTypes = BooleanSetting("-Xlog-free-types", "Print a message when reification resorts to generating a free type.")
  val maxClassfileName = IntSetting("-Xmax-classfile-name", "Maximum filename length for generated classes", 255, 72 to 255)
  val Xmigration28 = BooleanSetting("-Xmigration", "Warn about constructs whose behavior may have changed between 2.7 and 2.8.")
  val nouescape = BooleanSetting("-Xno-uescape", "Disable handling of \\u unicode escapes.")
  val Xnojline = BooleanSetting("-Xnojline", "Do not use JLine for editing.")
  val Xverify = BooleanSetting("-Xverify", "Verify generic signatures in generated bytecode (asm backend only.)")
  val plugin = MultiStringSetting("-Xplugin", "file", "Load one or more plugins from files.")
  val disable = MultiStringSetting("-Xplugin-disable", "plugin", "Disable the given plugin(s).")
  val showPlugins = BooleanSetting("-Xplugin-list", "Print a synopsis of loaded plugins.")
  val require = MultiStringSetting("-Xplugin-require", "plugin", "Abort unless the given plugin(s) are available.")
  val pluginsDir = StringSetting("-Xpluginsdir", "path", "Path to search compiler plugins.", Defaults.scalaPluginPath)
  val Xprint = PhasesSetting("-Xprint", "Print out program after")
  val writeICode = PhasesSetting("-Xprint-icode", "Log internal icode to *.icode files after", "icode")
  val Xprintpos = BooleanSetting("-Xprint-pos", "Print tree positions, as offsets.")
  val printtypes = BooleanSetting("-Xprint-types", "Print tree types (debugging option).")
  val prompt = BooleanSetting("-Xprompt", "Display a prompt after each error (debugging option).")
  val resident = BooleanSetting("-Xresident", "Compiler stays resident: read source filenames from standard input.")
  val script = StringSetting("-Xscript", "object", "Treat the source file as a script and wrap it in a main method.", "")
  val mainClass = StringSetting("-Xmain-class", "path", "Class for manifest's Main-Class entry (only useful with -d <jar>)", "")
  val Xshowcls = StringSetting("-Xshow-class", "class", "Show internal representation of class.", "")
  val Xshowobj = StringSetting("-Xshow-object", "object", "Show internal representation of object.", "")
  val showPhases = BooleanSetting("-Xshow-phases", "Print a synopsis of compiler phases.")
  val sourceReader = StringSetting("-Xsource-reader", "classname", "Specify a custom method for reading source files.", "")

  val XoldPatmat = BooleanSetting("-Xoldpatmat", "Use the pre-2.10 pattern matcher. Otherwise, the 'virtualizing' pattern matcher is used in 2.10.")
  val XnoPatmatAnalysis = BooleanSetting("-Xno-patmat-analysis", "Don't perform exhaustivity/unreachability analysis. Also, ignore @switch annotation.")
  val XfullLubs = BooleanSetting("-Xfull-lubs", "Retains pre 2.10 behavior of less aggressive truncation of least upper bounds.")

  /** -Y "Private" settings
   */
  val overrideObjects = BooleanSetting("-Yoverride-objects", "Allow member objects to be overridden.")
  val overrideVars = BooleanSetting("-Yoverride-vars", "Allow vars to be overridden.")
  val Yhelp = BooleanSetting("-Y", "Print a synopsis of private options.")
  val browse = PhasesSetting("-Ybrowse", "Browse the abstract syntax tree after")
  val check = PhasesSetting("-Ycheck", "Check the tree at the end of")
  val YcheckTypedTrees = BooleanSetting("-YcheckTypedTrees", "Check all constructured typed trees for type correctness")
  val Yshow = PhasesSetting("-Yshow", "(Requires -Xshow-class or -Xshow-object) Show after")
  val Xcloselim = BooleanSetting("-Yclosure-elim", "Perform closure elimination.")
  val Ycompacttrees = BooleanSetting("-Ycompact-trees", "Use compact tree printer when displaying trees.")
  val noCompletion = BooleanSetting("-Yno-completion", "Disable tab-completion in the REPL.")
  val Xdce = BooleanSetting("-Ydead-code", "Perform dead code elimination.")
  val debug = BooleanSetting("-Ydebug", "Increase the quantity of debugging output.")
  //val doc           = BooleanSetting    ("-Ydoc", "Generate documentation")
  val termConflict = ChoiceSetting("-Yresolve-term-conflict", "strategy", "Resolve term conflicts", List("package", "object", "error"), "error")
  val inline = BooleanSetting("-Yinline", "Perform inlining when possible.")
  val inlineHandlers = BooleanSetting("-Yinline-handlers", "Perform exception handler inlining when possible.")
  val YinlinerWarnings = BooleanSetting("-Yinline-warnings", "Emit inlining warnings. (Normally surpressed due to high volume)")
  val Xlinearizer = ChoiceSetting("-Ylinearizer", "which", "Linearizer to use", List("normal", "dfs", "rpo", "dump"), "rpo")
  val log = PhasesSetting("-Ylog", "Log operations during")
  val Ylogcp = BooleanSetting("-Ylog-classpath", "Output information about what classpath is being applied.")
  val Ynogenericsig = BooleanSetting("-Yno-generic-signatures", "Suppress generation of generic signatures for Java.")
  val noimports = BooleanSetting("-Yno-imports", "Compile without importing scala.*, java.lang.*, or Predef.")
  val nopredef = BooleanSetting("-Yno-predef", "Compile without importing Predef.")
  val noAdaptedArgs = BooleanSetting("-Yno-adapted-args", "Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.")
  val selfInAnnots = BooleanSetting("-Yself-in-annots", "Include a \"self\" identifier inside of annotations.")
  val Xshowtrees = BooleanSetting("-Yshow-trees", "(Requires -Xprint:) Print detailed ASTs in formatted form.")
  val XshowtreesCompact = BooleanSetting("-Yshow-trees-compact", "(Requires -Xprint:) Print detailed ASTs in compact form.")
  val XshowtreesStringified = BooleanSetting("-Yshow-trees-stringified", "(Requires -Xprint:) Print stringifications along with detailed ASTs.")
  val Yshowsyms = BooleanSetting("-Yshow-syms", "Print the AST symbol hierarchy after each phase.")
  val Yshowsymkinds = BooleanSetting("-Yshow-symkinds", "Print abbreviated symbol kinds next to symbol names.")
  val skip = PhasesSetting("-Yskip", "Skip")
  val Ygenjavap = StringSetting("-Ygen-javap", "dir", "Generate a parallel output directory of .javap files.", "")
  val Ydumpclasses = StringSetting("-Ydump-classes", "dir", "Dump the generated bytecode to .class files (useful for reflective compilation that utilizes in-memory classloaders).", "")
  val Ynosqueeze = BooleanSetting("-Yno-squeeze", "Disable creation of compact code in matching.")
  val stopAfter = PhasesSetting("-Ystop-after", "Stop after") withAbbreviation ("-stop") // backward compat
  val stopBefore = PhasesSetting("-Ystop-before", "Stop before")
  val refinementMethodDispatch = ChoiceSetting("-Ystruct-dispatch", "policy", "structural method dispatch policy", List("no-cache", "mono-cache", "poly-cache", "invoke-dynamic"), "poly-cache")
  val Yrangepos = BooleanSetting("-Yrangepos", "Use range positions for syntax trees.")
  val Ybuilderdebug = ChoiceSetting("-Ybuilder-debug", "manager", "Compile using the specified build manager.", List("none", "refined", "simple"), "none")
  val Yreifycopypaste = BooleanSetting("-Yreify-copypaste", "Dump the reified trees in copypasteable representation.")
  val Yreplsync = BooleanSetting("-Yrepl-sync", "Do not use asynchronous code for repl startup")
  val Ynotnull = BooleanSetting("-Ynotnull", "Enable (experimental and incomplete) scala.NotNull.")
  val YmethodInfer = BooleanSetting("-Yinfer-argument-types", "Infer types for arguments of overriden methods.")
  val etaExpandKeepsStar = BooleanSetting("-Yeta-expand-keeps-star", "Eta-expand varargs methods to T* rather than Seq[T].  This is a temporary option to ease transition.")
  val Yinvalidate = StringSetting("-Yinvalidate", "classpath-entry", "Invalidate classpath entry before run", "")
  val noSelfCheck = BooleanSetting("-Yno-self-type-checks", "Suppress check for self-type conformance among inherited members.")
  val YvirtClasses = false // too embryonic to even expose as a -Y //BooleanSetting    ("-Yvirtual-classes", "Support virtual classes")

  def stop = stopAfter

  /** Area-specific debug output.
   */
  val Ybuildmanagerdebug = BooleanSetting("-Ybuild-manager-debug", "Generate debug information for the Refined Build Manager compiler.")
  val Ycompletion = BooleanSetting("-Ycompletion-debug", "Trace all tab completion activity.")
  val Ydocdebug = BooleanSetting("-Ydoc-debug", "Trace all scaladoc activity.")
  val Yidedebug = BooleanSetting("-Yide-debug", "Generate, validate and output trees using the interactive compiler.")
  val Yinferdebug = BooleanSetting("-Yinfer-debug", "Trace type inference and implicit search.")
  val Yissuedebug = BooleanSetting("-Yissue-debug", "Print stack traces when a context issues an error.")
  val YmacrodebugLite = BooleanSetting("-Ymacro-debug-lite", "Trace essential macro-related activities.")
  val YmacrodebugVerbose = BooleanSetting("-Ymacro-debug-verbose", "Trace all macro-related activities: compilation, generation of synthetics, classloading, expansion, exceptions.")
  val Ypmatdebug = BooleanSetting("-Ypmat-debug", "Trace all pattern matcher activity.")
  val Yposdebug = BooleanSetting("-Ypos-debug", "Trace position validation.")
  val Yreifydebug = BooleanSetting("-Yreify-debug", "Trace reification.")
  val Yrepldebug = BooleanSetting("-Yrepl-debug", "Trace all repl activity.")
  val Ytyperdebug = BooleanSetting("-Ytyper-debug", "Trace all type assignments.")
  val Ypatmatdebug = BooleanSetting("-Ypatmat-debug", "Trace pattern matching translation.")

  val optimise = BooleanSetting("-optimise", "Generates faster bytecode by applying optimisations to the program") withAbbreviation "-optimize"

  /** IDE-specific settings
   */
  val YpresentationVerbose = BooleanSetting("-Ypresentation-verbose", "Print information about presentation compiler tasks.")
  val YpresentationDebug = BooleanSetting("-Ypresentation-debug", "Enable debugging output for the presentation compiler.")
  val YpresentationStrict = BooleanSetting("-Ypresentation-strict", "Do not report type errors in sources with syntax errors.")

  val YpresentationLog = StringSetting("-Ypresentation-log", "file", "Log presentation compiler events into file", "")
  val YpresentationReplay = StringSetting("-Ypresentation-replay", "file", "Replay presentation compiler events from file", "")
  val YpresentationDelay = IntSetting("-Ypresentation-delay", "Wait number of ms after typing before starting typechecking", 0, 0 to 999)
}