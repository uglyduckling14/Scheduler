

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class SchedulerSJF extends Scheduler{
    private int contextSwitches;
    private Queue<Process> queue;
    private Logger logger;

    public SchedulerSJF(Logger logger){
        this.logger = logger;
        queue = new PriorityQueue<>(new CompareJobs());
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

class CompareJobs implements Comparator<Process>{
    @Override
    public int compare(Process o1, Process o2) {
        return Integer.compare(o1.getBurstTime(), o2.getBurstTime());
    }
}

