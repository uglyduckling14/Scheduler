public class SchedulerSJF extends Scheduler{
    private int contextSwitches;

    public SchedulerSJF(Logger logger){

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
