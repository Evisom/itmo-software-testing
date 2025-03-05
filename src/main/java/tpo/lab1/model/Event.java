package tpo.lab1.model;

import tpo.lab1.model.enums.BlockResult;
import tpo.lab1.model.enums.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Event {
    private EventType type;
    private List<Person> participants;
    private Room location;
    private String cause;
    private BlockAttempt blockAttempt;

    public Event(EventType type, Room location, String cause, BlockAttempt blockAttempt) {
        this.type = type;
        this.location = location;
        this.cause = cause;
        this.participants = new ArrayList<>();
        this.blockAttempt = blockAttempt;
    }

    public void addParticipant(Person person) {
        participants.add(person);
    }

    public BlockResult getBlockResult() {
        return blockAttempt.getResult();
    }

    public List<Person> getParticipants() {
        return this.participants;
    }

    public EventType getType() {
        return this.type;
    }

    public BlockResult isSuccess(){
        if (participants.stream().mapToInt(Person::getForse).sum() > blockAttempt.getBlock()){
            return  BlockResult.SUCCESSFUL;

        }else{
            return  BlockResult.TSCHETNO;
        }
    }
}
