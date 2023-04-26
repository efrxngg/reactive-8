//package edu.spring.reactive.scheduled;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * El período de tiempo habla de una brecha de tiempo. No tiene fecha / hora de inicio.
// * Eg. 1 hora, 24 minutos, 3 días, 4 meses, 40 segundos, etc.
// * <br>
// * El punto del tiempo habla de un tiempo en particular. Además, tiene fecha / hora de inicio.
// * Eg. 4 AM, 15 de agosto, 26 de enero 9:00 AM, etc.
// */
//@Component
//public class ScheduledTask {
//
//    /**
//     * @Scheduled El método anotado no debe tener un tipo de retorno, ademas
//     * el método anotado no debe aceptar ningún parámetro de entrada.
//     */
//    private void init() {
//    }
//
//    /**
//     * FixedDelay especifica el intervalo de tiempo exacto entre la hora de finalización
//     * de la última ejecución del método y la hora de inicio del método siguiente
//     */
//    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
//    public void scheduledMethodFD() {
//        System.out.println("Init Delay :" + new Date());
//    }
//
//    /**
//     * Denota el intervalo de tiempo máximo entre la llamada al método.
//     */
//    @Scheduled(fixedRate = 5000)
//    public void scheduledMethodFR() {
//        System.out.println("Fixed Rate: " + new Date());
//    }
//
//    /**
//     * Utiliza expresiones cron para la programación, aquí también
//     * Spring boot incluye el mismo concepto internamente.
//     * <ul>
//     *     <li>Segundos (0-59)</li>
//     *     <li>Minutos (0-59)</li>
//     *     <li>Horas (0-23)</li>
//     *     <li>Día del mes (1-31)</li>
//     *     <li>Mes (1-12 o nombres abreviados)</li>
//     *     <li>Día de la semana (0-7 o nombres abreviados)</li>
//     * </ul>
//     */
//    @Scheduled(cron = "*/1 * * * * *")
//    public void scheduledMethod() {
//        System.out.println("Cron: " + new Date());
//    }
//
//}
