public class SchedulerRR extends Scheduler{
    private int contextSwitches;

    public SchedulerRR(Logger logger, int timeQuantum){

    }

    @Override
    public int getNumberOfContextSwitches() {
        return super.getNumberOfContextSwitches();
    }

    @Override
    void notifyNewProcess(Process process) {

    }

    @Override
    Process update(Process process, int cpu) {
        return null;
    }
}
