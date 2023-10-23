package persistance;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}


// CITATION: Writable.java from JavaSpecializationDemo, CPSC 210 Phase 2 edx