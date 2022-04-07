/// <reference types="cypress" />

describe('dummy site', () => {
  let data = undefined;

  before(() => {
    cy.fixture('example').then(function (fixture) {
      data = fixture;
    })
  })

  beforeEach(() => {
    // Return to homepage before each execution
    cy.visit(data.homepage)
  })

  it('button - check on click behavior', () => {

    // Check initial value
    cy.get('.js-button-count').should('have.text','0')

    // Check value increase
    cy.get('.js-button-counter-up').click()
    cy.get('.js-button-count').should('have.text','1')

    // Check value decrease
    cy.get('.js-button-counter-down').click()
    cy.get('.js-button-count').should('have.text','0')
  })

  it('input - check user data submit', () => {

    // Select the form, the within makes all "get" functions search inside the form,
    // so we don't need 'data-test-id' for each input
    cy.get('[data-test-id="form-submit-user-data"]').within(() => {

      // Check placeholders
      cy.get('input[name="firstname"]')
      .invoke('attr', 'placeholder')
      .should("eq", data.firstname_placeholder)

      cy.get('input[name="lastname"]')
      .invoke('attr', 'placeholder')
      .should("eq",data.lastname_placeholder)

      // fill and submit the form
      cy.get('input[name="firstname"]').type(data.firstname_value)
      cy.get('input[name="lastname"]').type(data.lastname_value)

      cy.get('button').click()

      // Check success page
      cy.location('pathname').should('contain', data.submit_segment)
    })
  })
})
