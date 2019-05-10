/*
 * Copyright 2018 BNY Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.codekatas.deckofcards.list.immutable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;

public class JDK8DeckOfCardsAsList
{
    private List<Card> cards;
    private Map<Suit, List<Card>> cardsBySuit;

    /**
     * TODO Replace the null values below with something more useful.
     * Use Java 8 Streams API with {@link Card#streamCards()} to create an "immutable" List and store that in cards.
     * Group all of the cards by {@link Card#suit} into an "immutable" Map and stored that in cardsBySuit.
     *
     * Hint: Look at {@link Collectors#groupingBy(Function)}, {@link Collections#unmodifiableList(List)}
     * {@link Collectors#collectingAndThen(Collector, Function)}, {@link Collectors#mapping(Function, Collector)}
     */
    public JDK8DeckOfCardsAsList()
    {
        this.cards = Card.streamCards().sorted().collect(Collectors.toUnmodifiableList());

        this.cardsBySuit = cards.stream().collect(
                Collectors.collectingAndThen(Collectors.groupingBy(Card::getSuit, Collectors.toUnmodifiableList()) ,
                        Collections::unmodifiableMap)
        );



    }

    public Deque<Card> shuffle(Random random)
    {
        // TODO Shuffle the deck 3 times with the Random parameter and push the shuffled cards onto an ArrayDeque
        // Hint: Look at IntStream.range() or IntStream.rangeClosed() and Collections.shuffle().
        var shuffled = new ArrayList<>(this.cards);
        IntStream.range(0,3).boxed().forEach( (int1) -> Collections.shuffle(shuffled,random));
        var deck = new ArrayDeque<Card>();
        shuffled.forEach(card -> deck.push(card));
        return deck;

    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        var hand = new HashSet<Card>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        var shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand)
    {
        // TODO Deal the number of hands with the cardsPerHand into an "immutable" List<Set<Card>>
        var result = new ArrayList<Set<Card>>();
        IntStream.range(0, hands).boxed().forEach((n) -> result.add(this.deal(shuffled, cardsPerHand)));
        return List.copyOf(result);
    }

    public List<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public List<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public List<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public List<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Map<Suit, Long> countsBySuit()
    {
        // TODO return the count of cards by Suit
        // Hint: Look at Collectors.groupingBy() and Collectors.counting()
        return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    public Map<Rank, Long> countsByRank()
    {
        // TODO return the count of cards by Rank
        // Hint: Look at Collectors.groupingBy() and Collectors.counting()
        return cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, List<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
