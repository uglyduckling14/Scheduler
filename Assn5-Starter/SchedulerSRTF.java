public class SchedulerSRTF extends Scheduler{
    private int contextSwitches;

    public SchedulerSRTF(Logger logger){

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
