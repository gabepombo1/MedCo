package MedCo3Final;

/**
     * Mini class for clock
     */
    public class Clock {
        //Clock variable fields
        private int seconds = 0;
        private int minutes = 0;
        private int hours = 1;
        private String time = "PM";

        public Clock() {
        }

        /**
         * Simulates a clock.
         * Advances 10 seconds every time it is called
         *
         * @return time as a string
         */
        @Override
        public String toString() {
            if (seconds >= 60) { // converts 60 seconds to 1 minute
                minutes++; // adds a minute
                seconds = 0; // resets seconds to 0
                if (minutes >= 60) { // converts 60 minutes to 1 hour
                    hours++; // adds an hour
                    minutes = 0; // resets minutes to 0
                    if (hours == 12) {
                        time = ("PM".equals(time)) ? "AM" : "PM";  // switches AM and PM
                    } else if (hours > 12) {
                        // resets hours to 1 in the morning
                        hours = 1;
                    }
                }
            }

            // Formats the time
            final String clockTime = hours + ":"
                    + ((minutes < 10) ? "0" : "") + minutes + ":"
                    + ((seconds == 0) ? "00" : seconds) + " " + time;

            seconds += 10; // increments seconds by 10
            return clockTime; // return the time as a string
        }
    }