package org.mock.addon.s3

import better.files.File
import io.findify.s3mock.S3Mock
import io.findify.s3mock.provider.FileProvider
import org.rogach.scallop.ScallopConf

/**
  * a standalone s3 mock server which take various parameters
  */
object S3MockStandaloneServer {
  def main(args: Array[String]): Unit = {
    val conf = new S3MockConf(args) // Note: This line also works for "object Main extends App"

    println("\nport are: " + conf.port())
    println("storage folder is: " + conf.storage())
    println("inmemory are: " + conf.inmemory() + "\n")

    val server =
      if (conf.inmemory.getOrElse(false))
        S3Mock(conf.port(), conf.storage())
      else
        S3Mock(conf.port())

    server.start

    println(".......s3mock server started...........")
  }

}

class S3MockConf(arguments: Seq[String]) extends ScallopConf(arguments) {
  version("1.0.0 ")

  banner(
    """Usage: S3MockStandaloneServer [OPTION]...
      |S3MockStandaloneServer is an s3 mock server for local test
      |""".stripMargin)

  footer("\nFor all other tricks, consult the documentation!")

  val DEFAULT_PORT = 7373
  val DEFAULT_STORAGE_DIR = "/tmp/s3mock"
  val DEFAULT_IN_MEMORY_MODE = false

  val port = opt[Int](default = Some(DEFAULT_PORT))
  val storage = opt[String](default = Some(DEFAULT_STORAGE_DIR))
  val inmemory = opt[Boolean](default = Some(DEFAULT_IN_MEMORY_MODE))

  verify()
}
