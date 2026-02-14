import {Component, Input, OnInit, output, signal} from '@angular/core';
import {DeckDTO} from '../../../api';
import {DeckCardOverviewComponent} from '../deck-card-overview/deck-card-overview.component';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-deck-overview',
  imports: [
    DeckCardOverviewComponent
  ],
  templateUrl: './deck-overview.component.html',
  styleUrl: './deck-overview.component.scss'
})
export class DeckOverviewComponent {
  @Input() deck:DeckDTO = {};
  showListView: boolean = true;
  onDelete = output();

  deleteDeck(): void {
    AppComponent.DECK_API
      .deleteId({id: this.deck.id!})
      .then(() => this.onDelete.emit());
  }
}
