package applications.robocode

import spock.lang.Specification

class TestClassCreation extends Specification {
    /* 
     * id : an id used in the generation of the name of the class.
     * enemy_energy : the coefficient for the enemy's energy
     * my_energy : the coefficient for our energy
     * angle_diff : the coefficient for the different in angles between us and the point and then and the point
     * distance : the coefficient for the distance between the point and the enemy
     */
    def id
    def enemy_energy
    def my_energy
    def angle_diff
    def distance
    def robotBuilder
    
    def setup() {
        Random random = new Random()
        id = random.nextInt(1000000)
        enemy_energy = random.nextFloat() * 100
        my_energy = random.nextFloat() * 100
        angle_diff = random.nextFloat() * 100
        distance = random.nextFloat() * 100
        robotBuilder = new RobotBuilder()
    }

    /*
     * Here we want to make sure that given some data, we can
     * use the templating tools to create a file containing
     * the Java source for a robot.
     */
    def "Confirm that we can create an Individual class"() {
        given:
        def values = ["id" : id, "enemy_energy" : enemy_energy, "my_energy" : my_energy, "angle_diff" : angle_diff, "distance" : distance]

        when:
        robotBuilder.buildJavaFile(values)
        
        then:
        confirmJavaFileExists()
    }
    
    def confirmJavaFileExists() {
        File file = new File("evolved_robots/Individual_${id}.java")
        def contents = file.readLines()
        def interestingLines = contents.findResults { line ->
            (line.indexOf("public class") > 0) || (line.indexOf("eval += ") > 0)
        }
        assert interestingLines.size() == 5
        assert interestingLines[0].indexOf("Individual_${id}") > 0
        assert interestingLines[1].indexOf("eval += (${enemy_energy})") > 0
        assert interestingLines[1].indexOf("eval += (${my_energy})") > 0
        assert interestingLines[1].indexOf("eval += (${angle_diff})") > 0
        assert interestingLines[1].indexOf("eval += (${distance})") > 0
    }
}
