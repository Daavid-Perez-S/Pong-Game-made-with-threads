/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Programación concurrente
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 26/09/2018 
 */

package Languages;

/**
 * @author David Pérez S.
 */
import java.io.IOException;
import java.util.Properties;
 
public class Language extends Properties{
 
    private static final long serialVersionUID = 1L;
 
    public Language(String languageName){
 
        //Modify if you want add more languages
        // Change the name of the files or add what you need
        switch(languageName){
            case "Spanish":
                    getProperties("SpanishProperties.properties");
                    break;
            case "English":
                    getProperties("EnglishProperties.properties");
                    break;
            default:
                    getProperties("SpanishProperties.properties");
        }
    }
 
    private void getProperties(String idioma) {
        try {
            this.load( getClass().getResourceAsStream(idioma) );
        } catch (IOException ex) {
 
        }
   }
}