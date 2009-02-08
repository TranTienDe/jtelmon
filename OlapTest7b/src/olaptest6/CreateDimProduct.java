package olaptest6;
/**
 * @author netlander
 */
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.palo.api.Connection;
import org.palo.api.Database;
import org.palo.api.Dimension;
import org.palo.api.Hierarchy;
import org.palo.api.Cube;
import org.palo.api.ConnectionFactory;
import org.palo.api.ConnectionConfiguration;
import org.palo.api.Element;
import org.palo.api.Consolidation;

public class CreateDimProduct {

static class Produs{
    private String denumire;
    private String clasa;
    private String grupa;
    private String categorie;
    public Produs(String denDep){
        this.denumire=denDep;
        this.clasa=null;
        this.categorie=null;
        this.grupa=null;
    }
    public Produs(String denProd, String denCls, String denCat, String denGru){
        this.denumire=denProd;
        this.clasa=denCls;
        this.categorie=denCat;
        this.grupa=denGru;
    }

    public void rename(String denProd){
        this.denumire=denProd;
    }
    String getDenumire(){
        return this.denumire;
    }
    public void addClasa(String denCls){
        this.clasa=denCls;
    }
    String getClasa(){
        return this.clasa;
    }
    public void addCategorie(String denCat){
        this.categorie=denCat;
    }
    String getCategorie(){
        return this.categorie;
    }
    public void addGrupa(String denGru){
        this.grupa=denGru;
    }
    String getGrupa(){
        return this.grupa;
    }
}


  static void CreateDimProduct002(){
    //String SQL_AgByDep = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, " +
    //         " ORDER BY categorie.denumire, numere_lucru.denumire ";
    //String SQL_Customer = " " +
    //    " SELECT tert_id, terti.denumire as client, " +
    //    " clasa.denumire as clasa, " +
    //    " grupa.denumire as grupa, " +
    //    " categorie.denumire as categorie " +
    //    " FROM  terti, categorie, grupa, clasa " +
    //    " WHERE terti.categorie_id=categorie.categ_id " +
	//    " AND terti.grupa_id=grupa.grupa_id " +
	//    " AND terti.clasa_id=clasa.clasa_id " +
	//    " AND terti.sw_0='a' " +
    //    " ORDER BY clasa, grupa, categorie ";

    String SQL_Product = " " +
     " select nomstoc.stoc_id, nomstoc.simbol, nomstoc.denumire, " +
     " clasa.denumire as clasa, grupa.denumire as grupa, " +
     " categorie.denumire as categorie from nomstoc, categorie, grupa, clasa  " +
     " where nomstoc.sw_0='a' AND  " +
     " nomstoc.categorie_id=categorie.categ_id  " +
     " AND nomstoc.grupa_id=grupa.grupa_id  " +
     " AND nomstoc.clasa_id=clasa.clasa_id  " +
     " ORDER BY clasa, grupa, categorie  " ;

    String SQL_Sursa = SQL_Product;
    List<Produs> ProductList = new ArrayList<Produs>();
    ProductList  =  getProductListBySQL (SQL_Sursa);
    // SetDimDepoAgByList(DepoAgList, "DepoAg");
}

  static List<Produs> getProductListBySQL(String SQL_sursa) {
    List<Produs> CustomerList = new ArrayList<Produs>();
    //String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    String url_sursa = "jdbc:postgresql://192.168.1.26:5432/pangram_week_2008";
    String username_sursa = "postgres";
    String password_sursa = "telinit";
    java.sql.Connection conn_sursa = null;
    java.sql.Statement stmt_sursa = null;
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Failed to load Postgres Driver");
        System.err.println(e);
    }
    try {
        conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
        stmt_sursa = conn_sursa.createStatement();
        System.out.println("Connection to " + url_sursa + " OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
        System.err.println(e);
    }
try {
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "_";
    String mS_denCli =" ", mS_denCls =" ", mS_denCat =" ", mS_denGru =" ";
    Client iClient = null;
    while (rs_sursa.next()) {
        mS_denCli = rs_sursa.getString("denumire").replaceAll(illegal_char, legal_char);
        mS_denCat = rs_sursa.getString("categorie").replaceAll(illegal_char, legal_char);
        mS_denGru = rs_sursa.getString("grupa").replaceAll(illegal_char, legal_char);
        mS_denCls = rs_sursa.getString("clasa").replaceAll(illegal_char, legal_char);
        System.out.println(mS_denCli + "  " + mS_denCls + "  " + mS_denGru + "  " + mS_denCat);
        if ( (iClient !=null) && ( iClient.getDenumire().equals(mS_denCli)) ){
            //iDepo.listOfAgents.add(mS_denAg);
            //if(mS_denDep.trim().equals("ARAD")){
            //    System.out.println(mS_denAg + "  " + Integer.toString(iDepo.listOfAgents.size()));
            //}
        }
        else{
            // iClient = new Client(" ");
            // iClient.rename(mS_denCli.trim());
            // CustomerList.add(iClient);
            // System.out.println(iDepo.getDen());
        }
    }
    System.out.println("Transfer OK");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return CustomerList;
    }


static class Client{
    private String denumire;
    private String clasa;
    private String grupa;
    private String categorie;
    public Client(String denDep){
        this.denumire=denDep;
        this.clasa=null;
        this.categorie=null;
        this.grupa=null;
    }
    public Client(String denDep, String denCls, String denCat, String denGru){
        this.denumire=denDep;
        this.clasa=denCls;
        this.categorie=denCat;
        this.grupa=denGru;
    }

    public void rename(String denDep){
        this.denumire=denDep;
    }
    String getDenumire(){
        return this.denumire;
    }
    public void addClasa(String denCls){
        this.clasa=denCls;
    }
    String getClasa(){
        return this.clasa;
    }
    public void addCategorie(String denCat){
        this.categorie=denCat;
    }
    String getCategorie(){
        return this.categorie;
    }
    public void addGrupa(String denGru){
        this.grupa=denGru;
    }
    String getGrupa(){
        return this.grupa;
    }
}

  static void CreateDimCustomer001(){
    //String SQL_AgByDep = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, " +
    //         " ORDER BY categorie.denumire, numere_lucru.denumire ";
    String SQL_Customer = " " +
        " SELECT tert_id, terti.denumire as client, " +
        " clasa.denumire as clasa, " +
        " grupa.denumire as grupa, " +
        " categorie.denumire as categorie " +
        " FROM  terti, categorie, grupa, clasa " +
        " WHERE terti.categorie_id=categorie.categ_id " +
	    " AND terti.grupa_id=grupa.grupa_id " +
	    " AND terti.clasa_id=clasa.clasa_id " +
	    " AND terti.sw_0='a' " +
        " ORDER BY clasa, grupa, categorie ";


    String SQL_Sursa = SQL_Customer;
    List<Client> CustomerList = new ArrayList<Client>();
    CustomerList  =  getCustomerListBySQL (SQL_Sursa);
    // SetDimDepoAgByList(DepoAgList, "DepoAg");
}

  static List<Client> getCustomerListBySQL(String SQL_sursa) {
    List<Client> CustomerList = new ArrayList<Client>();
    String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    // String url_sursa = "jdbc:postgresql://192.168.1.26:5432/pangram_week_2008";
    String username_sursa = "postgres";
    String password_sursa = "telinit";
    java.sql.Connection conn_sursa = null;
    java.sql.Statement stmt_sursa = null;
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Failed to load Postgres Driver");
        System.err.println(e);
    }
    try {
        conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
        stmt_sursa = conn_sursa.createStatement();
        System.out.println("Connection to source PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
        System.err.println(e);
    }
try {
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "";
    String mS_denCli =" ", mS_denCls =" ", mS_denCat =" ", mS_denGru =" ";
    Client iClient = null;
    while (rs_sursa.next()) {
        mS_denCli = rs_sursa.getString("client").replaceAll(illegal_char, legal_char);
        mS_denCat = rs_sursa.getString("categorie").replaceAll(illegal_char, legal_char);
        mS_denGru = rs_sursa.getString("grupa").replaceAll(illegal_char, legal_char);
        mS_denCls = rs_sursa.getString("clasa").replaceAll(illegal_char, legal_char);
        System.out.println(mS_denCli + "  " + mS_denCls + "  " + mS_denGru + "  " + mS_denCat);
        if ( (iClient !=null) && ( iClient.getDenumire().equals(mS_denCli)) ){
            //iDepo.listOfAgents.add(mS_denAg);
            //if(mS_denDep.trim().equals("ARAD")){
            //    System.out.println(mS_denAg + "  " + Integer.toString(iDepo.listOfAgents.size()));
            //}
        }
        else{
            // iClient = new Client(" ");
            // iClient.rename(mS_denCli.trim());
            // CustomerList.add(iClient);
            // System.out.println(iDepo.getDen());
        }
    }
    System.out.println("Transfer OK");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return CustomerList;
    }


 static class Depozit{
    private String denumire;
    public ArrayList <String> listOfAgents;
    public Depozit(String denDep){
        this.denumire=denDep;
        this.listOfAgents = new ArrayList <String>();
    }
    public void rename(String denDep){
        this.denumire=denDep;
    }
    String getDen(){
        return this.denumire;
    }
    public void addAg(String denAg){
        // this.denumire=denDep;
        this.listOfAgents.add(denAg);
    }
}

  static void CreateDimDepoAg(){
    // SQL4 AGENTI & DEPOZITE
    String SQL_AgByDep = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, " +
             " numere_lucru.categorie_id, categorie.denumire as categorie_denumire, " +
             " numere_lucru.grupa_id, numere_lucru.clasa_id "+
             " FROM numere_lucru, categorie " +
             " WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' " +
             " ORDER BY categorie.denumire, numere_lucru.denumire ";
    String SQL_Sursa = SQL_AgByDep;
    List<Depozit> DepoAgList = new ArrayList<Depozit>();
    DepoAgList  =  getDepoAgListBySQL (SQL_Sursa);
    SetDimDepoAgByList(DepoAgList, "DepoAg");
}


  static List<Depozit> getDepoAgListBySQL(String SQL_sursa) {
    List<Depozit> listOfDepos = new ArrayList<Depozit>();
    String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    // String url_sursa = "jdbc:postgresql://192.168.1.26:5432/pangram_week_2008";
    String username_sursa = "postgres";
    String password_sursa = "telinit";
    java.sql.Connection conn_sursa = null;
    java.sql.Statement stmt_sursa = null;
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Failed to load Postgres Driver");
        System.err.println(e);
    }
    try {
        conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
        stmt_sursa = conn_sursa.createStatement();
        System.out.println("Connection to source PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
        System.err.println(e);
    }
try {
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "";
    String mS_denDep =" ", mS_denAg =" ";
    Depozit iDepo = null;
    while (rs_sursa.next()) {
        mS_denAg = rs_sursa.getString("nick").replaceAll(illegal_char, legal_char);
        mS_denDep = rs_sursa.getString("categorie_denumire").replaceAll(illegal_char, legal_char);
        if ( (iDepo !=null) && ( iDepo.getDen().equals(mS_denDep)) ){
            iDepo.listOfAgents.add(mS_denAg);
            if(mS_denDep.trim().equals("ARAD")){
                System.out.println(mS_denAg + "  " + Integer.toString(iDepo.listOfAgents.size()));
            }
        }  
        else{
            iDepo = new Depozit(" ");
            iDepo.rename(mS_denDep.trim());
            listOfDepos.add(iDepo);
            // System.out.println(iDepo.getDen());
        }
    }
    System.out.println("Transfer OK");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return listOfDepos;
    }

static void SetDimDepoAgByList( List<Depozit> DepoAgList, String mS_dimensiune ){
    Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    Database odb = conn_dest.addDatabase("pangram_olap6");
try {
    Iterator vItr2 = DepoAgList.iterator();
    Dimension[] dimensions = new Dimension[1];
    Dimension dim1 = odb.addDimension(mS_dimensiune);
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    dimensions[0]=dim1;
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire, mS_DenDep;
    ArrayList <String> listOfAgents;
    Element parent, child;
    int index2=0, size=0;
    Consolidation[] consolidations ;
    Iterator vItr = null;
    while (vItr2.hasNext()){
        Depozit iDepo;
        iDepo = (Depozit) vItr2.next();
        mS_denumire = iDepo.getDen();
        if(!mS_denumire.substring(0, 1).equals("0")){
            mS_denumire = mS_denumire.replaceAll(illegal_char, legal_char);
            System.out.println(" __DEPOZIT__ " + mS_denumire);
            hie1.addElement( mS_denumire , Element.ELEMENTTYPE_NUMERIC);
            index2=0;
            parent = hie1.getElementByName(mS_denumire);
            listOfAgents = iDepo.listOfAgents;
            size = listOfAgents.size();
            System.out.println(" __NR.Agenti = " + Integer.toString(size));
            consolidations = new Consolidation[size];
            vItr = listOfAgents.iterator();
            while (vItr.hasNext()){
                mS_DenDep = (String) vItr.next();
                System.out.println(" agent : " + mS_DenDep);
                child = hie1.addElement(mS_DenDep, Element.ELEMENTTYPE_NUMERIC);
                consolidations[index2] = hie1.newConsolidation(child, parent, 1);
                index2=index2+1;
            }
            parent.updateConsolidations(consolidations);
            // break;
        }
    }
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
}

static void TheBigCube001() {
    // Agentii TM
    String[] aS_denumire = new String[100];
    int index1=0;
    String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    String username_sursa = "postgres";
    String password_sursa = "telinit";
    java.sql.Connection conn_sursa = null;
    java.sql.Statement stmt_sursa = null;
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Failed to load Postgres Driver");
    }
    try {
        conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
        stmt_sursa = conn_sursa.createStatement();
        System.out.println("Connection to source PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
    }
try {
    String SQL_sursa = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, numere_lucru.categorie_id, categorie.denumire as categorie_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, categorie WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' AND numere_lucru.categorie_id = '0249' ORDER BY numere_lucru.denumire ";
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire;
    while (rs_sursa.next()) {
        mS_denumire = rs_sursa.getString("nick");
        aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        System.out.println(aS_denumire[index1]);
        index1=index1+1;
    }
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1));
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
    ConnectionConfiguration ccfg = new ConnectionConfiguration("127.0.0.1", "7777");
    ccfg.setUser("admin");
    ccfg.setPassword("admin");
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    // Database demo2 =  conn.getDatabaseByName("Demo");
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Consolidation2");
    } catch (Exception e) {
        System.err.println("Cube already created");
    }
    Dimension[] dimensions = new Dimension[3];
    Dimension dim1 = demo2.addDimension("dimWarehouse");
    Dimension dim2 = demo2.addDimension("dimYears");
    Dimension dim3 = demo2.addDimension("dimCustomers");
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    Hierarchy hie2 = dim2.getDefaultHierarchy();
    Hierarchy hie3 = dim3.getDefaultHierarchy();
    dimensions[0]=dim1;
    dimensions[1]=dim2;
    dimensions[2]=dim3;
    hie1.addElement("Timisoara", Element.ELEMENTTYPE_NUMERIC);
    hie1.addElement("Bucuresti", Element.ELEMENTTYPE_NUMERIC);
    hie1.addElement("Cluj", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
    int index2;
    parent = hie1.getElementByName("Timisoara");
    Consolidation[] consolidations = new Consolidation[index1];
    for(index2=0;index2<index1;index2++ ){
        child = hie1.addElement(aS_denumire[index2], Element.ELEMENTTYPE_NUMERIC);
        consolidations[index2] = hie1.newConsolidation(child, parent, 1);
    }
    parent.updateConsolidations(consolidations);
    Cube cube = null;
    try {
        cube = demo2.addCube("cubeSales", dimensions);
    } catch (Exception e) {
        System.err.println("Cube creation failed");
    }
}

public static void nomain(String[] args) {
    // CreateDimDepoAg();
    // CreateDimCustomer001();
    CreateDimProduct002();
}
}
