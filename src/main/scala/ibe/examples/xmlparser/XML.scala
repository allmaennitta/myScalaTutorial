package ibe.examples.xmlparser

object XML {
  val StartTagAndContentPattern = "<([^\\/]*?)>(.*?)".r
  val ContentAndEndTagPattern = "^"

  def main (args: Array[String]): Unit ={
    parseXML(args(0))
  }

  def parseXML(xml : String)  =  {
    val StartTagAndContentPattern(name, content) = xml
    println(s"Name: $name; Content: $content")

    (name, content)

  }
//    if(word == null || word.length() <= 1)
//      return word;
//    else if( word.charAt(0) == word.charAt(1) )
//      return parseXML(word.substring(1, word.length()));
//    else
//      return word.charAt(0) +
//        parseXML(word.substring(1, word.length()));
//  }
}

class Tag (name : String, content : Array[Tag]){
  def getName = name
  def getContent = content
}

//      val Text = "<div><div>this text contains <span> a subtext </span> and a main text</div> and is part of a main text.</div>"
