package com.tasktracker.ml

import android.content.Context
import com.tasktracker.data.model.TaskLabel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskClassifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Keywords for each category
    private val educationKeywords = setOf(
        "study", "homework", "class", "lecture", "assignment", "exam", "test",
        "research", "reading", "book", "learn", "course", "tutorial", "practice",
        "quiz", "project", "thesis", "paper", "essay", "lab", "seminar", "workshop"
    )
    
    private val personalKeywords = setOf(
        "shopping", "groceries", "appointment", "call", "email", "birthday",
        "family", "friend", "dinner", "lunch", "breakfast", "cook", "clean",
        "laundry", "errands", "bills", "banking", "doctor", "dentist", "haircut",
        "pet", "garden", "home", "repair", "maintenance"
    )
    
    private val activityKeywords = setOf(
        "gym", "workout", "run", "running", "exercise", "yoga", "fitness",
        "sports", "walk", "walking", "jog", "jogging", "swim", "swimming",
        "bike", "cycling", "hike", "hiking", "dance", "dancing", "training",
        "cardio", "strength", "stretch", "meditation", "pilates"
    )
    
    private val workKeywords = setOf(
        "meeting", "presentation", "deadline", "report", "client", "project",
        "conference", "call", "email", "review", "planning", "strategy",
        "budget", "proposal", "contract", "interview", "training", "team",
        "manager", "boss", "colleague", "office", "work", "job", "task"
    )
    
    private val healthKeywords = setOf(
        "medicine", "medication", "pills", "doctor", "hospital", "clinic",
        "therapy", "treatment", "checkup", "health", "wellness", "sleep",
        "rest", "relax", "mental", "physical", "nutrition", "diet", "water",
        "vitamins", "supplements"
    )
    
    private val socialKeywords = setOf(
        "party", "event", "gathering", "meetup", "hangout", "date", "concert",
        "movie", "theater", "restaurant", "bar", "club", "celebration",
        "wedding", "birthday", "anniversary", "visit", "catch up", "chat",
        "coffee", "drinks", "friends", "social"
    )
    
    /**
     * Classifies a task based on its title and description
     * Returns the predicted label and confidence score (0.0 to 1.0)
     */
    fun classifyTask(title: String, description: String = ""): Pair<TaskLabel, Float> {
        val text = "$title $description".lowercase()
        val words = text.split(Regex("\\s+|[,.]"))
        
        // Count matches for each category
        val scores = mutableMapOf<TaskLabel, Int>()
        
        words.forEach { word ->
            when {
                educationKeywords.contains(word) -> 
                    scores[TaskLabel.EDUCATION] = scores.getOrDefault(TaskLabel.EDUCATION, 0) + 1
                personalKeywords.contains(word) -> 
                    scores[TaskLabel.PERSONAL] = scores.getOrDefault(TaskLabel.PERSONAL, 0) + 1
                activityKeywords.contains(word) -> 
                    scores[TaskLabel.ACTIVITY] = scores.getOrDefault(TaskLabel.ACTIVITY, 0) + 1
                workKeywords.contains(word) -> 
                    scores[TaskLabel.WORK] = scores.getOrDefault(TaskLabel.WORK, 0) + 1
                healthKeywords.contains(word) -> 
                    scores[TaskLabel.HEALTH] = scores.getOrDefault(TaskLabel.HEALTH, 0) + 1
                socialKeywords.contains(word) -> 
                    scores[TaskLabel.SOCIAL] = scores.getOrDefault(TaskLabel.SOCIAL, 0) + 1
            }
        }
        
        // Find the category with the highest score
        val maxEntry = scores.maxByOrNull { it.value }
        
        return if (maxEntry != null && maxEntry.value > 0) {
            // Calculate confidence based on match count
            val totalWords = words.size.coerceAtLeast(1)
            val confidence = (maxEntry.value.toFloat() / totalWords.toFloat()).coerceIn(0f, 1f)
            
            // Only return the classification if confidence is above threshold
            if (confidence >= CONFIDENCE_THRESHOLD) {
                Pair(maxEntry.key, confidence)
            } else {
                Pair(TaskLabel.CUSTOM, 0f)
            }
        } else {
            Pair(TaskLabel.CUSTOM, 0f)
        }
    }
    
    /**
     * Suggests possible labels for a task
     * Returns up to 3 suggestions sorted by confidence
     */
    fun suggestLabels(title: String, description: String = ""): List<Pair<TaskLabel, Float>> {
        val text = "$title $description".lowercase()
        val words = text.split(Regex("\\s+|[,.]"))
        
        val scores = mutableMapOf<TaskLabel, Int>()
        
        words.forEach { word ->
            when {
                educationKeywords.contains(word) -> 
                    scores[TaskLabel.EDUCATION] = scores.getOrDefault(TaskLabel.EDUCATION, 0) + 1
                personalKeywords.contains(word) -> 
                    scores[TaskLabel.PERSONAL] = scores.getOrDefault(TaskLabel.PERSONAL, 0) + 1
                activityKeywords.contains(word) -> 
                    scores[TaskLabel.ACTIVITY] = scores.getOrDefault(TaskLabel.ACTIVITY, 0) + 1
                workKeywords.contains(word) -> 
                    scores[TaskLabel.WORK] = scores.getOrDefault(TaskLabel.WORK, 0) + 1
                healthKeywords.contains(word) -> 
                    scores[TaskLabel.HEALTH] = scores.getOrDefault(TaskLabel.HEALTH, 0) + 1
                socialKeywords.contains(word) -> 
                    scores[TaskLabel.SOCIAL] = scores.getOrDefault(TaskLabel.SOCIAL, 0) + 1
            }
        }
        
        val totalWords = words.size.coerceAtLeast(1)
        
        return scores
            .map { (label, count) ->
                val confidence = (count.toFloat() / totalWords.toFloat()).coerceIn(0f, 1f)
                Pair(label, confidence)
            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .take(3)
    }
    
    companion object {
        private const val CONFIDENCE_THRESHOLD = 0.15f
    }
}
