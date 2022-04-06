/// <reference types="cypress" />

describe('dummy site', () => {

  it('button - check on click behavior', () => {

    cy.visit('http://localhost:8000/public/index.html')

    // Check initial value
    cy.get('.js-button-count').should('have.text','0')

    // Check value increase
    cy.get('.js-button-counter-up').click()
    cy.get('.js-button-count').should('have.text','1')

    // Check value decrease
    cy.get('.js-button-counter-down').click()
    cy.get('.js-button-count').should('have.text','0')
  })
})
