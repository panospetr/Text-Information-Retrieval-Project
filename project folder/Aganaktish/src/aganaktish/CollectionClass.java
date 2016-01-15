package aganaktish;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

/**H klash auth analamvanei thn diaxeirish twn syllogwn kai thn dhmiourgia tous. 
 * Oi methodoi ths einai static kai energopoiountai apo ta antistoixa parathyra ths MainWindow.java
 * 
 * @author Theo
 */
public class CollectionClass {
    /** Kaleitai kata thn energopoihsh tou programmatos kai fortwnei sto systhma tis yparxouses sylloges
     * Oi fakeloi twn syllogwn vriskontai panta ston idio fakelo me to ektelesimo arxeio.
     * 
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    
    
    
    public static void loadCollections() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Sylloges.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Aganaktish.Sylloges = (ArrayList) ois.readObject();
    }
    /**Dhmiourgei mia nea syllogh. Lamvanontas ws phgh to arxeia pou vriskete sto path twn parapmetrwn kalei tis
     * antistoixes synarthseis analoga me to an ta egrafa proerxontai apo ena arxeio h apo polla.
     * 
     * @param txtPath       Arxeio apo to opoio lamvanoume ta egrafa
     * @param collectionName        Onoma ths sylloghs
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    public static void createCollection(String txtPath, String collectionName) throws IOException, NullPointerException, ClassNotFoundException {
        File myFile = new File("Sylloges\\" + collectionName);
        myFile.mkdir();
        String pathNewCollection = myFile.getAbsolutePath();
        if (txtPath.endsWith(".txt")) {
            createCollectionOneTxtFile(txtPath, pathNewCollection);
        } else {
            createCollectionSeveralTxt(txtPath, pathNewCollection);
        }
        Aganaktish.Sylloges.add(collectionName);
        saveCollections();
    }
    /**Dhmiourgei mia syllogh apo polla arxeia kai ton antestrameno katalogo ths. Dhladh ta egrafa einai hdh spasmena se txt apla dn ta exoume sto systhma
     * O fakelos ston opoio dhmiourgeitai h syllogh einai o fakelos sylloges o opoios vriskete sto idio arxeio me to ektelesimo
     * kai periexei oles tis sylloges kai toys katalogous tous
     * 
     * @param txtPath           Arxeio to opoio perxiexei ola ta txt
     * @param pathNewCollection         O fakelos ston opoio tha mpei h syllogh (paragetai apo to programma)
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    private static void createCollectionSeveralTxt(String txtPath, String pathNewCollection) throws FileNotFoundException, IOException, NullPointerException, ClassNotFoundException {
        File txtNew = new File(pathNewCollection + "\\txt");
        txtNew.mkdir();
        File txtOld = new File(txtPath);
        FileChannel source = null;
        FileChannel destination = null;
        String[] txt = txtOld.list();
        TreeMap<String, ArrayList<Pair>> tempCatalog = new TreeMap();
        for (String temp : txt) {
            File tempTxt = new File(txtPath + "\\" + temp);
            File tempNewTxt = new File(pathNewCollection + "\\txt\\" + temp);
            source = new FileInputStream(tempTxt).getChannel();
            destination = new FileOutputStream(tempNewTxt).getChannel();
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            tempCatalog = Catalog.createCatalog(tempCatalog, pathNewCollection + "\\txt\\" + temp);
        }
        Aganaktish.collectionCatalog = tempCatalog;
        Catalog.saveCatalog(pathNewCollection);

    }
    /**Dhmiourgei mia syllogh apo ena arxeio kai ton antestrameno katalogo ths. Dhladh ta egrafa vriskontai ola se ena txt to ena meta to allo.
     * O fakelos ston opoio dhmiourgeitai h syllogh einai o fakelos sylloges o opoios vriskete sto idio arxeio me to ektelesimo
     * kai periexei oles tis sylloges kai toys katalogous tous
     * 
     * 
     * @param txtPath        Arxeio to opoio perxiexei to txt apo to opoio pernoume ta egrafa
     * @param pathNewCollection      O fakelos ston opoio tha mpei h syllogh (paragetai apo to programma)
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    private static void createCollectionOneTxtFile(String txtPath, String pathNewCollection) throws IOException, NullPointerException, ClassNotFoundException {
        //pairnei os orisma to arxeio p exei ola ta txt mazi

        //Diavazoume to arxeio me ola ta txt
        BufferedReader in = new BufferedReader(new FileReader(txtPath));

        //Dimiourgeia kainouriou fakelou ston fakelo tis syllogis pou tha apothikeytoun ola ta txt
        String path = pathNewCollection + "\\txt";

        File myTxt = new File(path);
        myTxt.mkdir();

        //Xekiname antigrafi ton txt
        String filename = ".I ", tempStr;
        int i = 1;
        String j;
        tempStr = in.readLine();
        TreeMap<String, ArrayList<Pair>> tempCatalog = new TreeMap();
        do {
            j = Integer.toString(i);
            if (tempStr.equals(filename + j)) {
                File newfile = new File(path + "\\" + j + ".txt");
                PrintWriter pw = new PrintWriter(new FileWriter(newfile));
                tempStr = "";
                String checkTitle = in.readLine();
                if (checkTitle.equals(".T")) {
                    checkTitle = in.readLine();
                    while (!checkTitle.equals(".A")) {
                        tempStr = tempStr + checkTitle;
                        checkTitle = in.readLine();
                    }
                    while (!checkTitle.equals(".W")) {
                        checkTitle = in.readLine();
                    }

                }

                j = Integer.toString(i + 1);
                while ((tempStr != null) && (!tempStr.equals(filename + j))) {
                    tempStr = removespaces(tempStr);
                    pw.print(tempStr);
                    tempStr = in.readLine();
                }
                pw.close();
                tempCatalog = Catalog.createCatalog(tempCatalog, path + "\\" + Integer.toString(i) + ".txt");
                i++;
            }
        } while (tempStr != null);

        in.close();
        Aganaktish.collectionCatalog = tempCatalog;
        Catalog.saveCatalog(pathNewCollection);

    }
    /**Anoigei mia syllogh pou epelexe o xrhsths fortwnontas ton katalogo ths.
     * Se authn thn syllogh ginete h ypovolh erwthmatwn
     * 
     * @param collection        To onoma ths sylloghs pou epelexe o xrhsths.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static void openCollection(String collection) throws FileNotFoundException, IOException, ClassNotFoundException {
        File myFile = new File("Sylloges\\" + collection);
        Aganaktish.collectionPath = myFile.getAbsolutePath();
        File temp = new File(Aganaktish.collectionPath + "\\txt");
        Aganaktish.docNum = temp.list().length;
        Catalog.loadCatalog();
    }
    /**Kleinei thn syllogh pou eixe anoixei o xrhsths kai ektelei oles tis diadikasies pou einai aparaitites
     * gia thn apothkeush allagwn pou eginan s authn.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    public static void closeCollection() throws FileNotFoundException, IOException, NullPointerException, ClassNotFoundException {
        Catalog.closeCatalog();
        Aganaktish.collectionPath = "";
    }
    /**Apothikeuei ston disko to arxeio sto opoio to programma krataei ola ta dedomena ta opoia einai aparaithta
     * gia thn anakthsh twn syllogwn sthn epomenh ekinhsh tou programmatos
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void saveCollections() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("Sylloges.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Aganaktish.Sylloges);
        oos.flush();
        oos.close();
    }
/**Energopoiei tis diadikasies prostheshs newn arxeiwn sthn epilegmenh syllogh
 * Analoga me to an ta arxeia einai sygentrwmena se ena txt h einai spasmena se polla kalei tis analoges synarthseis
 * 
 * @param path      To monopati sto opoio vriskete o fakelos me ta arxeia twn newn egrafwn
 * @throws FileNotFoundException
 * @throws IOException 
 */
    public static void addToCollection(String path) throws FileNotFoundException, IOException {
        if (path.endsWith(".txt")) {
            addToCollectionOneTxt(path);
        } else {
            addToCollectionSeveralTxt(path);
        }
        Aganaktish.newCatalog = 1;
    }
/**Prosthetei sthn epilegmenh syllogh nea egrafa ta opoia vriskontai sygentrwmena se ena arxeio txt 
 * 
 * @param path       To monopati sto opoio vriskete o fakelos me to arxeio txt twn newn egrafwn
 * @throws FileNotFoundException
 * @throws IOException 
 */
    private static void addToCollectionOneTxt(String path) throws FileNotFoundException, IOException {
        File txtOld = new File(path);
        FileChannel source = null;
        FileChannel destination = null;
        File txtFolder = new File(Aganaktish.collectionPath + "\\txt\\");
        String[] collectionTxt = txtFolder.list();
        ArrayList<String> collectionTxtList = new ArrayList();
        collectionTxtList.addAll(Arrays.asList(collectionTxt));
        String txtName = txtOld.getName();
        if (collectionTxtList.contains(txtName)) {
            int i = 1;
            do {
                txtName = txtName.substring(0, txtName.lastIndexOf(".txt")) + " (" + i + ").txt";
                i++;
            } while (collectionTxtList.contains(txtName));
        }
        String newTxtPath = Aganaktish.collectionPath + "\\txt\\" + txtName;
        File tempNewTxt = new File(newTxtPath);
        source = new FileInputStream(txtOld).getChannel();
        destination = new FileOutputStream(tempNewTxt).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        Catalog.InsertIntoCatalog(newTxtPath);
    }
/** Prosthetei sthn epilegmenh syllogh nea egrafa ta opoia vriskontai spasmena se arxeia txt
 * 
 * @param path      To monopati sto opoio vriskete o fakelos me ta arxeia txt twn newn egrafwn
 * @throws FileNotFoundException
 * @throws IOException 
 */
    private static void addToCollectionSeveralTxt(String path) throws FileNotFoundException, IOException {
        File txtOld = new File(path);
        FileChannel source = null;
        FileChannel destination = null;
        String[] txt = txtOld.list();
        File txtFolder = new File(Aganaktish.collectionPath + "\\txt\\");
        String[] collectionTxt = txtFolder.list();
        ArrayList<String> collectionTxtList = new ArrayList();
        collectionTxtList.addAll(Arrays.asList(collectionTxt));
        for (String temp : txt) {
            String txtName = temp;
            if (collectionTxtList.contains(txtName)) {
                int i = 1;
                do {
                    txtName = txtName.substring(0, txtName.lastIndexOf(".txt")) + " (" + i + ").txt";
                    i++;
                } while (collectionTxtList.contains(txtName));
            }
            File tempTxt = new File(path + "\\" + temp);
            String newTxtPath = Aganaktish.collectionPath + "\\txt\\" + txtName;
            File tempNewTxt = new File(newTxtPath);
            source = new FileInputStream(tempTxt).getChannel();
            destination = new FileOutputStream(tempNewTxt).getChannel();
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            Catalog.InsertIntoCatalog(newTxtPath);
        }
    }
    /**Diagrafei to egrafo txt pou vriskete stis parametrous apo mia syllogh kai enhmerwnei ton katalogo ths
     * 
     * @param txtName       Onoma tou egrafou pros diagrafh
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void deleteTxt(String txtName) throws FileNotFoundException, IOException{
        txtName=txtName+".txt";
        String path = Aganaktish.collectionPath+"\\txt\\"+txtName;
        File myTxt = new File(path);
        Catalog.deleteFromCatalog(path);
        myTxt.delete();
        Aganaktish.newCatalog=1;
    }
/** Apaloifei ta kena kai tis paules sto telos mias grammhs kata tin anagnwsh ths apo ta arxeia txt. Me auton ton tropo lexeis pou dn
 * xwrousan se mia seira dn kovontai stin mesh
 * 
 * @param line      Grammh pros epeksergasia.
 * @return 
 */
    private static String removespaces(String line) {
        int length;
        while (line.endsWith(" ")) {
            length = line.length();
            line = line.substring(0, length - 1);
        }
        while (line.startsWith(" ")) {
            length = line.length();
            line = line.substring(1, length);
        }
        if (line.endsWith("-")) {
            length = line.length();
            line = line.substring(0, length - 1);
            return (line);
        } else {
            return (line + " ");
        }

    }
}
