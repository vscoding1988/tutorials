import {Component, OnInit, signal} from '@angular/core';
import {DeckDTO} from '../../../api';
import {DeckOverviewComponent} from '../deck-overview/deck-overview.component';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-deck-page',
  imports: [
    DeckOverviewComponent
  ],
  templateUrl: './deck-page.component.html',
  styleUrl: './deck-page.component.scss'
})
export class DeckPageComponent implements OnInit {
  decks = signal<Array<DeckDTO>>([]);
  selectedDeck= signal<DeckDTO | null>(null);

  ngOnInit(): void {
    this.loadDecks();
  }

  loadDecks(): void {
    this.selectedDeck.set(null);
    AppComponent.DECK_API.getDecks()
      .then(response => this.decks.set(response));
  }

  selectDeck(deck:DeckDTO){
    AppComponent.DECK_API.getDeckById({id: deck.id!})
      .then(result => {
        this.selectedDeck.set(result);
      });
  }
}
