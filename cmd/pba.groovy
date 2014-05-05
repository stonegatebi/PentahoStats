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


@Usage("Pentaho Business Analytics Server")
class pba {

  @Usage("Show the Users in the BA platform")
  @Command
  public void users (@User String user,@Password String password, @Hostname String hostname) {
      def http = new RESTClient( 'http://' + hostname + ':8080/pentaho/' )
      // do the first get to pass auth params. The plugin and api cannot use auth params.
     if (user == null) {
       user = "admin"
     }
     if (password == null) {
       password = "password"
     }
     if (hostname == null) {
       hostname = "localhost"
     }
      http.get( path: 'Home', query: [userid: user, password: password] )
      def resp = http.get( path: 'plugin/PentahoStats/api/bis_users_active', query:  [userid: user, password: password] )
      def data = resp.getData()
      def str = resp.data.text
      //out.show(str)
      def json = new groovy.json.JsonSlurper().parseText(str)
      def header = ["ID", "Start", "Name", "End", "Days"]
      out.println(header)
      json.resultset.each { row ->
         out.println(row)
      } //end loop connections
    } //end users method
    
  @Usage("Show the Schedules n the BA platform")
  @Command
  public String schedules () {
      return "Schedules Here: Not implemented yet. Stay tuned!!"
  }
    
  @Usage("Show the content in the BA platform")
  @Command
  public String content () {
      return "Content Here: Not implemented yet. Stay tuned!!"
 }


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
