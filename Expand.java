import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Expand {

    public static String carpeta = "src";

    private static List<String> leidos; //se crea una lista para ir guardando los archivos que se leen

    public static void main(String[] args) { //para que se pueda llamar desde el terminal
        leidos = new ArrayList<String>();
        try {
            lectorVarios(args); //toma los archivos ingresados y los "entrega" a lectorVarios()
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isArchivo(String palabra){ //verifica si una palabra es un archivo
        for(int i=0; i < 3; i++){
            if(palabra.charAt(i) != '<' || palabra.charAt(palabra.length()-i-1) != '>' ){
                return false;
            }
        }
        return true;
    }


    public static String lector(String fileName) throws IOException { //lee un archivo completo

        leidos.add(fileName); //se agrega el archivo a la lista de leidos
        String resultado = ""; //se crea un string que entregará el resultado
        String linea;

        // La clase FileReader permite abrir un archivo para lectura
        FileReader readableFile = new FileReader(carpeta+"/"+fileName);
        // La clase BufferedReader permite leer del FileReader anterior
        BufferedReader reader = new BufferedReader(readableFile);

        linea = reader.readLine();

        while (linea != null) { //se lee el archivo
            for (String palabra : linea.split(" ")) { //se separa la linea en palabras y se itera sobre ellas
                if (isArchivo(palabra)){ //se verifica si la palabra es un archivo
                    String nuevoArchivo = palabra.substring(3,palabra.length()-3);
                    if (leidos.contains(nuevoArchivo)){ //si lo es y ya se ha leido, hay un loop y el programa se detiene
                        System.out.print("Error: Loop de referencias de archivos");
                        System.exit(0);
                    }
                    else{ //si es un archivo, y no se ha leido, se lee recursivamente y se agrega al resultado
                        resultado += lector(palabra.substring(3,palabra.length()-3));
                    }
                }
                else {  //si no es un archivo, se agrega al resultado
                    resultado += palabra + " ";
                }

            }
            resultado += "\n";
            linea = reader.readLine();
        }

        leidos.remove(fileName); //una vez que se leyo un archivo este se quita de la lista para poder ser leido nuevamente
        // Luego de leer es necesario cerrar el archivo
        reader.close();
        readableFile.close();

        return resultado;
    }

    public static void lectorVarios(String[] archivos) throws IOException { //funcion que lee varios archivos
        for (String archivo : archivos) { //se itera sobre los archivos
            System.out.print(lector(archivo)); //se lee cada archivo y se imprime el "resultado" se cada uno
        }
    }
}

