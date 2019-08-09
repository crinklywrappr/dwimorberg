(defproject dwimorberg "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.jscience/jscience "4.3.1"]
                 [org.apfloat/apfloat "1.9.0"]
                 [criterium "0.4.5"]]
  :repl-options {:init-ns dwimorberg.core}
  :source-paths ["src/clj"]
  :java-source-paths ["src/jvm"]
  :java-cmd "C:\\Program Files\\OpenJDK\\jdk-11.0.2\\bin\\java.exe"
  :jvm-opts ["-server"
             "-Dcom.sun.management.jmxremote"
             "-Dcom.sun.management.jmxremote.local.only=false"
             "-Dcom.sun.management.jmxremote.authenticate=false"
             "-Dcom.sun.management.jmxremote.ssl=false"])
