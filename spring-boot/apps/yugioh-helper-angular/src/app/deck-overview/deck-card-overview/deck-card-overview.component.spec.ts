import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeckCardOverviewComponent } from './deck-card-overview.component';

describe('DeckCardOverviewComponent', () => {
  let component: DeckCardOverviewComponent;
  let fixture: ComponentFixture<DeckCardOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeckCardOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeckCardOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
