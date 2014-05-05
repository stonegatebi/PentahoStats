@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import org.crsh.cli.Man
import org.crsh.cli.Argument
import org.crsh.cli.Option
import org.crsh.cli.Required
import org.crsh.util.Utils


@Usage("Pentaho Data Integration Server")
class pdi {

  @Usage("Status of the Data Integration Server")
  @Command
  public void status (@User String user,@Password String password, @Hostname String hostname) {
      def http = new RESTClient( 'http://' + hostname + ':9080' )
     if (user == null) {
       user = "cluster"
     }
     if (password == null) {
       password = "cluster"
     }
     if (hostname == null) {
       hostname = "localhost"
     }
      def resp = http.get( path: 'pentaho-di/status', query: [user: user, pass: password] )
      def data = resp.getData()
      def str = resp.data.text
      out.show(str)
    } //end status method
}

@Retention(RetentionPolicy.RUNTIME)
@Usage("The username")
@Man("The username")
@Option(names=["u","username"])
public @interface User { }

@Retention(RetentionPolicy.RUNTIME)
@Usage("The password")
@Man("The password")
@Option(names=["p","password"])
public @interface Password { }

@Retention(RetentionPolicy.RUNTIME)
@Usage("The hostname")
@Man("The hostname")
@Option(names=["h","hostname"])
public @interface Hostname { }
