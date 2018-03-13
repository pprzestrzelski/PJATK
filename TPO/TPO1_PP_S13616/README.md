Tre�� zadania:

Katalog {user.home}/TPO1dir  zawiera pliki tekstowe umieszczone w tym katalogu i jego r�nych podkatalogach. Kodowanie plik�w to Cp1250. 
Przegl�daj�c rekursywnie drzewo katalogowe, zaczynaj�ce si� od {user.home}/TPO1dir,  wczytywa� te pliki i dopisywa� ich zawarto�ci do pliku o nazwie TPO1res.txt, znaduj�cym si� w katalogu projektu. Kodowanie pliku TPO1res.txt winno by� UTF-8, a po ka�dym uruchomieniu programu plik ten powinien zawiera� tylko aktualnie przeczytane dane z  plik�w katalogu/podkatalogow. 

Poni�szy gotowy fragment winien wykona� ca�� robot�:
      public class Main {
        public static void main(String[] args) {
          String dirName = System.getProperty("user.home")+"/TPO1dir";
          String resultFileName = "TPO1res.txt";
          Futil.processDir(dirName, resultFileName);
        }
      }
Uwagi:
pliku Main.java nie wolno w �aden spos�b modyfikowa�,
trzeba dostarczy� definicji klasy Futil,
oczywi�cie, nazwa katalogu i pliku oraz ich po�o�enie s� obowi�zkowe,
nale�y zastosowa� FileVisitor do przegl�dania katalogu oraz kana�y plikowe (klasa FileChannel) do odczytu/zapisu plik�w (bez tego rozwi�zanie nie uzyska punkt�w).
w wynikach test�w mog� by� przedstawione dodatkowe zalecenia co do sposobu wykonania zadania (o ile rozwi�zanie nie b�dzie jeszcze ich uwzgl�dnia�),.