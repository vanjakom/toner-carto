(defproject com.mungolab/simple-tile-server "0.1.0-SNAPSHOT"
  :description "simple tile server to be used with toner maps"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
  	[com.mungolab/clj-common "0.2.0-SNAPSHOT"]]
  :aot [simple-tile-server.server]
  :main simple-tile-server.server)
