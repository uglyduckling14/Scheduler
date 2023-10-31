import java.util.LinkedList;
import java.util.Queue;

public class SchedulerRR extends Scheduler{
    private int contextSwitches;
    private Queue<Process> queue;
    private Logger logger;
    private int timeQuantum;
    public SchedulerRR(Logger logger, int timeQuantum){
        this.logger = logger;
        queue = new LinkedList<>();
        this.timeQuantum = timeQuantum;
    }

    @Override
    public int getNumberOfContextSwitches() {
        return contextSwitches;
    }

    @Override
    void notifyNewProcess(Process process) {
        queue.add(process);
    }

    @Override
    Process update(Process process, int cpu) {
        if(process==null && queue.peek()!=null){
            Process p = queue.poll();
            logger.log("CPU "+ cpu + " > Scheduled " + p.getName());
            return p;
        }
        if(process==null){
            return null;
        }
        if(process.isExecutionComplete()){
            logger.log("CPU " + cpu + " > Process " + process.getName() + " burst complete");
            logger.log("CPU " + cpu + " > Process " + process.getName() + " execution complete");
            if(queue.peek()!=null) {
                logger.log("CPU " + cpu + " > Scheduled " + queue.peek().getName());
            }
            contextSwitches+=2;
            return queue.poll();
        }
        if(process.isBurstComplete()) {
            logger.log("CPU " + cpu + " > Process " + process.getName() + " burst complete");
            notifyNewProcess(process);
            if(queue.peek()!=null) {
                logger.log("CPU " + cpu + " > Scheduled " + queue.peek().getName());
            }
            contextSwitches+=2;
            return queue.poll();
        }
        Process p = queue.peek();
        if(p!=null && ((process.getElapsedBurst())%timeQuantum)==0){
            logger.log("CPU "+ cpu + " > Time quantum complete for " + process.getName());
            logger.log("CPU "+ cpu + " > Scheduled " +p.getName());
            notifyNewProcess(process);
            contextSwitches+=2;
            return queue.poll();
        }
        return process;
    }
}
