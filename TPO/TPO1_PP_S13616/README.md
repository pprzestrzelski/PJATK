Treœæ zadania:

Katalog {user.home}/TPO1dir  zawiera pliki tekstowe umieszczone w tym katalogu i jego ró¿nych podkatalogach. Kodowanie plików to Cp1250. 
Przegl¹daj¹c rekursywnie drzewo katalogowe, zaczynaj¹ce siê od {user.home}/TPO1dir,  wczytywaæ te pliki i dopisywaæ ich zawartoœci do pliku o nazwie TPO1res.txt, znaduj¹cym siê w katalogu projektu. Kodowanie pliku TPO1res.txt winno byæ UTF-8, a po ka¿dym uruchomieniu programu plik ten powinien zawieraæ tylko aktualnie przeczytane dane z  plików katalogu/podkatalogow. 

Poni¿szy gotowy fragment winien wykonaæ ca³¹ robotê:
      public class Main {
        public static void main(String[] args) {
          String dirName = System.getProperty("user.home")+"/TPO1dir";
          String resultFileName = "TPO1res.txt";
          Futil.processDir(dirName, resultFileName);
        }
      }
Uwagi:
pliku Main.java nie wolno w ¿aden sposób modyfikowaæ,
trzeba dostarczyæ definicji klasy Futil,
oczywiœcie, nazwa katalogu i pliku oraz ich po³o¿enie s¹ obowi¹zkowe,
nale¿y zastosowaæ FileVisitor do przegl¹dania katalogu oraz kana³y plikowe (klasa FileChannel) do odczytu/zapisu plików (bez tego rozwi¹zanie nie uzyska punktów).
w wynikach testów mog¹ byæ przedstawione dodatkowe zalecenia co do sposobu wykonania zadania (o ile rozwi¹zanie nie bêdzie jeszcze ich uwzglêdniaæ),.