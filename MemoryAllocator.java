public class MemoryAllocator {

    private final int[] memory;   // 0 = free, 1 = occupied
    private final int[] owner;    // owner[i] = id of the block holding unit i, 0 = none
    private int nextId = 1;       // atomic counter, bumped only on a successful alloc

    MemoryAllocator(int[] memory) {
        this.memory = memory.clone();          // don't mutate the caller's array
        this.owner = new int[memory.length];   // pre-occupied 1s start with no owner
    }

    // Find the left-most run of x free units, occupy it, stamp it with a fresh id.
    // Returns the first index of the block, or -1 if no such run exists.
    int alloc(int x) {
        int run = 0;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] != 0) {
                run = 0;            // occupied unit breaks the run
                continue;
            }
            run++;
            if (run == x) {
                int start = i - x + 1;
                for (int j = start; j <= i; j++) {
                    memory[j] = 1;
                    owner[j] = nextId;
                }
                nextId++;           // only successful allocs advance the counter
                return start;
            }
        }
        return -1;
    }

    // Free every unit stamped with this id. Returns how many units were freed,
    // or -1 if the id was never handed out / its block is already gone.
    int erase(int id) {
        if (id <= 0) {
            return -1;              // 0 means "unowned", never a real block id
        }
        int freed = 0;
        for (int i = 0; i < memory.length; i++) {
            if (owner[i] == id) {
                memory[i] = 0;
                owner[i] = 0;
                freed++;
            }
        }
        return freed == 0 ? -1 : freed;
    }

    // Challenge signature: run the queries in order, collect one answer each.
    // Queries look like "alloc 5" / "erase 2".
    static int[] solution(int[] memory, String[] queries) {
        MemoryAllocator allocator = new MemoryAllocator(memory);
        int[] answers = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int space = queries[i].indexOf(' ');
            String op = queries[i].substring(0, space);
            int arg = Integer.parseInt(queries[i].substring(space + 1));
            answers[i] = op.equals("alloc") ? allocator.alloc(arg) : allocator.erase(arg);
        }
        return answers;
    }

    public static void main(String[] args) {
        // step-by-step trace on a memory with one pre-occupied unit at index 3
        MemoryAllocator m = new MemoryAllocator(new int[]{0, 0, 0, 1, 0, 0});
        System.out.println(m.alloc(2)); // 0   -> id 1 takes [0,1]
        System.out.println(m.alloc(3)); // -1  -> only runs of 1 and 2 are left
        System.out.println(m.erase(1)); // 2   -> frees [0,1]
        System.out.println(m.erase(1)); // -1  -> id 1 is already gone
        System.out.println(m.alloc(3)); // 0   -> id 2 takes [0,1,2]
        System.out.println(m.erase(7)); // -1  -> id 7 was never handed out

        // same run through the challenge signature
        System.out.println(java.util.Arrays.toString(solution(
                new int[]{0, 0, 0, 1, 0, 0},
                new String[]{"alloc 2", "alloc 3", "erase 1", "erase 1", "alloc 3", "erase 7"})));
        // [0, -1, 2, -1, 0, -1]

        // ids keep counting up across blocks; failed allocs don't consume one
        System.out.println(java.util.Arrays.toString(solution(
                new int[]{0, 0, 0, 0},
                new String[]{"alloc 1", "alloc 9", "alloc 1", "erase 2", "erase 3"})));
        // [0, -1, 1, 1, -1]   <- "alloc 9" failed, so the second block is id 2
    }
}
