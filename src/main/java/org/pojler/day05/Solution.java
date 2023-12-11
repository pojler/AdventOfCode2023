package org.pojler.day05;

import org.apache.commons.lang3.Range;
import org.pojler.utils.FileReader;

import java.util.*;

public class Solution {

    public void execute () {
        FileReader fileReader = new FileReader();
        String content = fileReader.fileAsString("day5");
        String[] splitContent = content.split("\n\n");
        long[] seeds = getSeeds(splitContent[0]);
        List<Range<Long>> ranges = getRangeList(seeds);
        Long[][] stsMap = getMap(splitContent[1]);
        Long[][] stfMap = getMap(splitContent[2]);
        Long[][] ftwMap = getMap(splitContent[3]);
        Long[][] wtlMap = getMap(splitContent[4]);
        Long[][] lttMap = getMap(splitContent[5]);
        Long[][] tthMap = getMap(splitContent[6]);
        Long[][] htlMap = getMap(splitContent[7]);
        long smallestLocationId = Long.MAX_VALUE;
        long smallestLocationIdForAllTheSeeds= Long.MAX_VALUE;
        for(long seed : seeds) {
            long current = seed;
            for(Long[] sts: stsMap) {
                if(sts[1] > current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            for(Long[] sts: stfMap) {
                if(sts[1] > current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            for(Long[] sts: ftwMap) {
                if(sts[1] > current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            for(Long[] sts: wtlMap) {
                if(sts[1] > current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            for(Long[] sts: lttMap) {
                if(sts[1] >  current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            for(Long[] sts: tthMap) {
                if(sts[1] > current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            for(Long[] sts: htlMap) {
                if(sts[1] > current) {
                    continue;
                }
                if(sts[1]+sts[2] >= current) {
                    current = sts[0] + (current - sts[1]);
                    break;
                }
            }
            if(current < smallestLocationId ){
                smallestLocationId = current;
            }
        }
        int progress = 0;
        for(Range<Long> range : ranges) {
            for(long seed = range.getMinimum(); seed <= range.getMaximum(); seed ++){
                long current = seed;
                for(Long[] sts: stsMap) {
                    if(sts[1] > current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                for(Long[] sts: stfMap) {
                    if(sts[1] > current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                for(Long[] sts: ftwMap) {
                    if(sts[1] > current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                for(Long[] sts: wtlMap) {
                    if(sts[1] > current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                for(Long[] sts: lttMap) {
                    if(sts[1] >  current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                for(Long[] sts: tthMap) {
                    if(sts[1] > current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                for(Long[] sts: htlMap) {
                    if(sts[1] > current) {
                        continue;
                    }
                    if(sts[1]+sts[2] -1 >= current) {
                        current = sts[0] + (current - sts[1]);
                        break;
                    }
                }
                if(current < smallestLocationIdForAllTheSeeds ){
                    smallestLocationIdForAllTheSeeds = current;
                }
            }
            System.out.println(progress);
            progress++;
        }
        System.out.println(smallestLocationId);
        System.out.println(smallestLocationIdForAllTheSeeds);
    }

    public List<Range<Long>> getRangeList (long[] seeds) {
        List<Range<Long>> result = new ArrayList<>();
        for(int i = 0; i < seeds.length ; i += 2) {
            result.add(Range.of(seeds[i], seeds[i] + seeds[i+1] -1));
        }
        return result;
    }


    public long[] getSeeds (String seeds) {
        String[] splitSeeds = seeds.split(" ");
        long[] result = new long[splitSeeds.length-1];
        for(int i = 1; i < splitSeeds.length; i++) {
            result[i-1] = Long.parseLong(splitSeeds[i]);
        }
        return result;
    }

    public Long [][] getMap (String mapString) {
        String [] splitMap = mapString.split("\n");
        Long[][] result = new Long[splitMap.length-1][3];
        for (int i = 1; i < splitMap.length; i++) {
            result[i-1] = Arrays.stream(splitMap[i].split(" ")).map(Long::parseLong).toArray(Long[]::new);
        }
        return result;
    }

}
