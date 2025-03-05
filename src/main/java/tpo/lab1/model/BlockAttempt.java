package tpo.lab1.model;

import tpo.lab1.model.enums.BlockResult;

import java.util.List;

public class BlockAttempt {
    private List<Person> blockers;
    private List<Person> intruders;
    private BlockResult result;

    public BlockAttempt(List<Person> blockers, List<Person> intruders, BlockResult result) {
        this.blockers = blockers;
        this.intruders = intruders;
        this.result = result;
    }
    public BlockAttempt(){

    }

    public BlockResult getResult() { return result; }
}