package Main;

public class Pair<L, S> {
        private final L first;
        private final S  second;

        public Pair(L first, S second) {
            this.first = first;
            this.second = second;
        }

        
        public L getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }
        public String toString() {
            return "Main.Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null ) {
                return false;
            }
            Pair<L, S> pair = (Pair<L, S>) obj;
            return first.equals(pair.first) && second == pair.second;
        }
    }
