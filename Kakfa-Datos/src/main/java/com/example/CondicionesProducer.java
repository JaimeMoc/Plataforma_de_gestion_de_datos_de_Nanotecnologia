// Jaime Alberto Suarez Noctezuma. 
// Importación de las líbrerias que vanos a necesitar.
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

// Creación de la clase Condiciones Producer. 
public class CondicionesProducer {
    // Logger para registrar eventos e información del programa
    private static final Logger logger = Logger.getLogger(CondicionesProducer.class.getName());

    public static void main(String[] args) {
        // Configuración del logger para registrar eventos en un archivo de log
        try {
            FileHandler fh = new FileHandler("CondicionesProducer.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Configuración del productor de Kafka utilizando un archivo de propiedades
        Properties producerProps = new Properties();
       
        try (FileInputStream fis = new FileInputStream("producer.properties")) {
            producerProps.load(fis); // Carga las configuraciones desde un archivo externo
        } catch (IOException e) {
            e.printStackTrace();
            return;  // Finaliza si no se pueden cargar las propiedades
        }
        // Inicialización del productor de Kafka con las propiedades configuradas
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);
        // Inicialización de valores simulados para los experimentos
        Random random = new Random();
        int[] temperaturas = {300, 400, 500, 600};  // Valores típicos de temperatura en experimentos
        double[] presiones = {1.0, 1.5, 2.0, 2.5};  // Presiones aplicadas en atmósferas
        int[] duraciones = {30, 60, 120, 180};      // Duraciones de los experimentos en minutos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Generación de mensajes para el tópico condiciones-topic
        for (int i = 1; i <= 10; i++) {  // Generar 10 mensajes
            int idExperimento = random.nextInt(1000) + 1;  // ID aleatorio del experimento
            int idMaterial = random.nextInt(100) + 1;      // ID aleatorio del material
            int temperatura = temperaturas[random.nextInt(temperaturas.length)]; // Temperatura aleatoria.
            double presion = presiones[random.nextInt(presiones.length)]; // Presión aleatoria.
            int duracion = duraciones[random.nextInt(duraciones.length)]; //Duración aleatoria.
            String fechaExperimento = LocalDate.now().minusDays(random.nextInt(365)).format(formatter); // Fecha aleatoria en el ultimo año.

            // Creación del mensaje JSON
            String condicionesJson = String.format(
                "{\"id_experimento\":%d,\"id_material\":%d,\"temperatura\":%d,\"presion_aplicada\":%.1f,\"duracion_experimento\":%d,\"fecha_experimento\":\"%s\"}",
                idExperimento, idMaterial, temperatura, presion, duracion, fechaExperimento
            );

            // Enviar mensaje al tópico condiciones-topic
            try {
                producer.send(new ProducerRecord<>("condiciones-topic", Integer.toString(idExperimento), condicionesJson));
                logger.info("Mensaje enviado: " + condicionesJson);
            } catch (Exception e) {
                logger.severe("Error enviando mensaje: " + e.getMessage());
            }
        }
        // Cerramos el productor
        producer.close();
    }
}