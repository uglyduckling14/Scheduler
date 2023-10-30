import java.util.LinkedList;
import java.util.Queue;

public class SchedulerFCFS extends Scheduler {
    private int contextSwitches;
    private final Logger logger;
    Queue<Process> queue;
    public SchedulerFCFS(Logger logger){
        this.logger = logger;
        queue = new LinkedList<>();
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
        if(process==null){
            Process p = queue.poll();
            logger.log("CPU "+ cpu + " > Scheduled " + p.getName());
            return p;
        }
        if(process.isBurstComplete() && !process.isExecutionComplete()) {
            logger.log("CPU " + cpu + " > Process " + process.getName() + " burst complete");
            notifyNewProcess(process);
            logger.log("CPU "+ cpu + " > Scheduled " + queue.peek().getName());
            contextSwitches+=2;
            return queue.poll();
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
        return process;
    }
}
