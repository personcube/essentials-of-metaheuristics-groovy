package singleStateMethods

import problems.OnesMax
import groovy.transform.ToString


/*  this is a clone from Nic by Matt */
class HillClimber {		
	// Happily this ended up being an almost direct copy from Sean's book.
	def maximize(problem) {
		def s = problem.create()
		def sQuality = problem.quality(s)
		while (!problem.terminate(s, sQuality)) {
			def r = problem.tweak(problem.copy(s))
			def rQuality = problem.quality(r)
			if (rQuality > sQuality) {
				s = r
				sQuality = rQuality
			}
		}
		return s
	}
	
	String toString() {
		"HC"
	}
}
