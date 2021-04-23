import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;
import javax.swing.JOptionPane;
public class Pelicula implements Comparable<Pelicula> {

    Scanner sc = new Scanner(System.in);
    public int codigo, alquiler, socio;
    public String titulo;

    public boolean activa = true;
    Pelicula[] peliculas = new Pelicula[20];
    Pelicula[] peliculaBuscada = new Pelicula[peliculas.length];

    //Constructor de la peliboletosa chavales 
    public Pelicula(int codigo, String titulo, int alquiler, int socio, boolean activa) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.alquiler = alquiler;
        this.socio = socio;
        this.activa = activa;

    }

    //Constructor para evitar errores en instancias
    public Pelicula() {

    }



    @Override
    public int compareTo(Pelicula o) {
        if (this.codigo < o.codigo) {
            return -1;
        }
        if (this.codigo > o.codigo) {
            return 1;
        }
        return 0;
    }

    public int ordenarVector() {
        int index = 0;
        for (int i = 0; i < peliculas.length; i++) {
            if (peliculas[i] == null) {

                break;

            } else {

                index++;

            }

        }
        Arrays.sort(peliculas, 0, index);
        return index;
    }
    
    
    
    public void limpiarVector(){
        for (int i = 0; i < peliculas.length; i++) {
            peliculaBuscada[i] = null;
            
        }    
    }
    
    public void GuardarEnDisco(){
        ordenarVector();
        eliminacionFisica();
        String PeliculasActuales = "";
        
        for (int i = 0; i < peliculas.length; i++) {
            if (peliculas[i] == null) {
                                break;
                
            }else{
                if(peliculas[i].activa==true){
                PeliculasActuales += peliculas[i].codigo+","+peliculas[i].titulo+","+peliculas[i].alquiler+","+peliculas[i].socio+","+peliculas[i].activa+"\n";
                }
              
            }
        }
        
        try{
            PrintWriter pw = new PrintWriter("test\\Peliculas.txt");
            pw.print(PeliculasActuales);
            pw.close();
        }catch (Exception err){
            JOptionPane.showMessageDialog(null, "error al guardar en el txt");
        }
    }
    
   public void LeerDisco(){
        
        String line;
        String Peliculatxt = "";
        String path = "test\\Peliculas.txt";
        File file = new File(path);
        
        try{
            if (!file.exists()) {
                file.createNewFile();
            }else{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null){
                    if (!line.isEmpty()) {
                       Peliculatxt += line + "\n"; 
                    }
                }
                if (!"".equals(Peliculatxt)) {
                    String[] PeliculasSplit = Peliculatxt.split("\n");
                    for (int i = 0; i < PeliculasSplit.length; i++) {
                        String[] PeliculasSplit2 = PeliculasSplit[i].split(",");
                         peliculas[i] = new Pelicula(Integer.parseInt(PeliculasSplit2[0]),PeliculasSplit2[1],Integer.parseInt(PeliculasSplit2[2]),Integer.parseInt(PeliculasSplit2[3]),Boolean.parseBoolean(PeliculasSplit2[4]));
                    }
                }
                br.close();
            }
        }catch (IOException ex){
            System.out.println("error al leer el txt");
        }
    }
  

    public Pelicula[] ConsultaPorPalabras(String palabra){
        limpiarVector();
        for (int i = 0; i < 20; i++) {
            peliculaBuscada[i]=null;
        }
        
        int contador = 0;
        String[] Palabra = palabra.toLowerCase().split(" ");                              //agarra la palabra y la divide por cada espacio que encuetra
        for (int i = 0; i < 20; i++) {
            
            if (peliculas[i]==null) {
                break;
            }
            String[] TituloPelicula = peliculas[i].titulo.toLowerCase().split(" ");  //agarra el título y lo divide por cada espacio que encuentra
            
            if (Palabra.length==2) {
                if (TituloPelicula.length==2) {
                    if (TituloPelicula[0].equals(Palabra[0]) && TituloPelicula[1].equals(Palabra[1])) { //si las dos primeras palabras de la búsqueda y del título son iguales
                    peliculaBuscada[contador]=peliculas[i];
                    contador++;
                    } 
                }                  
            }else if(Palabra.length==1){
                if (TituloPelicula[0].equals(Palabra[0])) {
                    peliculaBuscada[contador]=peliculas[i];
                    contador++;
                }else if(TituloPelicula.length==2){
                    if (TituloPelicula[1].equals(Palabra[0])) {
                        peliculaBuscada[contador]=peliculas[i];
                        contador++;
                    }
                }
            }
            
            
        } 
        return peliculaBuscada;
    }
  
    public int busquedaBinaria(int codigox) {
        int inicio = 0;
        int fin = ordenarVector()-1;

        while (inicio <= fin) {              
           int pos = (inicio + fin) / 2;
            if (peliculas[pos].codigo == codigox) {
                return pos;
            } else if (peliculas[pos].codigo < codigox) {
                inicio = pos + 1;
            } else {
                fin = pos - 1;
            }
        }
        return -1;
    }
    

    
    

    public void agregarPelicula(Pelicula peli) {

        for (int i = 0; i < peliculas.length; i++) {

            if (peliculas[i] == null) {
                this.peliculas[i] = peli;
                JOptionPane.showMessageDialog(null, "La película fue registrada con exito");
                    ordenarVector();
                    
                break;

            } else {
                if (peli.codigo == peliculas[i].codigo) {

                    break;
                }

            }

        }

    }

    public void eliminacionFisica() {
        ordenarVector();
        Pelicula[] auxiliar = new Pelicula[peliculas.length];
        int aux = 0;
        for (int i = 0; i < auxiliar.length; i++) {

            if (peliculas[i] == null) {
                break;
            }
            if (peliculas[i].activa == true) {
                auxiliar[aux] = peliculas[i];
                aux++;

            }

        }
      
        peliculas = auxiliar;
   
    }

    public void eliminacionLogica(int codigo) {

        if(codigo==-1){
            JOptionPane.showMessageDialog(null, "No existe película con ese código");
        }else{
            peliculas[codigo].activa= false;
            peliculas[codigo].codigo=-1;
            JOptionPane.showMessageDialog(null, "Pelicula pre-Eliminada, cierre y vuelva a abrir el programa para guardar el cambio de manera definitiva con éxito");

            
            
        }
        


    }

}
